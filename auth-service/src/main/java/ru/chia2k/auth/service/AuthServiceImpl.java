package ru.chia2k.auth.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chia2k.auth.dto.JwtRequest;
import ru.chia2k.auth.dto.JwtResponse;
import ru.chia2k.auth.dto.JwtTokenResponse;
import ru.chia2k.auth.entity.AppEntity;
import ru.chia2k.auth.entity.RefreshTokenEntity;
import ru.chia2k.auth.entity.UserEntity;
import ru.chia2k.auth.exception.AuthException;
import ru.chia2k.auth.repository.AppRepository;
import ru.chia2k.auth.repository.RefreshTokenRepository;
import ru.chia2k.security.client.authentication.JwtAuthentication;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final Integer refreshTokenLimit;
    private final UserService userService;
    private final RefreshTokenRepository repository;
    private final JwtProvider jwtProvider;
    private final AppRepository appRepository;
    private final PasswordEncoder passwordEncoder;
    private final Object lockApps = new Object();

    private volatile Set<String> apps;

    public AuthServiceImpl(
            @Value("${application.jwt.token-count-limit:3}") Integer refreshTokenLimit,
            UserService userService,
            RefreshTokenRepository repository,
            JwtProvider jwtProvider,
            AppRepository appRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.refreshTokenLimit = refreshTokenLimit;
        this.userService = userService;
        this.repository = repository;
        this.jwtProvider = jwtProvider;
        this.appRepository = appRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @CircuitBreaker(name = "authServiceCircuitBreaker")
    @Bulkhead(name="authServiceBulkhead")
    @RateLimiter(name = "authServiceRateLimiter")
    @Transactional
    @Override
    public JwtResponse login(@NonNull JwtRequest authRequest) {
        if (!checkApp(authRequest.getApp())) {
            throw new AuthException("Invalid application");
        }

        try {
            final Integer accountNumber = Integer.valueOf(authRequest.getLogin());
            final UserEntity user = userService.getByAccountNumber(accountNumber)
                    .orElseThrow(() -> new AuthException("Invalid credentials"));

            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                final String refreshTokenId = generateTokenId();
                final String accessToken = jwtProvider.generateAccessToken(user, refreshTokenId);
                final String refreshToken = jwtProvider.generateRefreshToken(user, refreshTokenId);
                Claims accessClaims = jwtProvider.getAccessClaims(accessToken);
                Claims refreshClaims = jwtProvider.getRefreshClaims(refreshToken);


                RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
                refreshTokenEntity.setId(refreshTokenId);
                refreshTokenEntity.setApp(authRequest.getApp());
                refreshTokenEntity.setRefreshToken(refreshToken);
                refreshTokenEntity.setSubject(refreshClaims.getSubject());
                refreshTokenEntity.setExpiration(refreshClaims.getExpiration());
                repository.save(refreshTokenEntity);

                processRefreshTokenCountLimits(refreshClaims.getSubject(), authRequest.getApp());

                return JwtResponse.builder()
                        .accessToken(accessToken)
                        .accessTokenExpiration(accessClaims.getExpiration())
                        .refreshToken(refreshToken)
                        .refreshTokenExpiration(refreshClaims.getExpiration())
                        .build();
            } else {
                throw new AuthException("Invalid credentials");
            }
        } catch (NumberFormatException e) {
            throw new AuthException("Invalid credentials");
        }
    }

    @CircuitBreaker(name = "authServiceCircuitBreaker")
    @Bulkhead(name="authServiceBulkhead")
    @Transactional(readOnly = true)
    @Override
    public JwtTokenResponse getNewAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String tokenId = claims.getId();
            final String subject = claims.getSubject();

            Integer accountNumber;
            try {
                accountNumber = Integer.valueOf(subject);
            } catch (NumberFormatException e) {
                throw new AuthException("Invalid token");
            }

            var savedRefreshTokenEntity = repository.findById(tokenId);
            if (savedRefreshTokenEntity.isPresent()
                    && savedRefreshTokenEntity.get().getRefreshToken().equals(refreshToken)) {

                final UserEntity user = userService.getByAccountNumber(accountNumber)
                        .orElseThrow(() -> new AuthException("Invalid token"));

                final String accessToken = jwtProvider.generateAccessToken(user, tokenId);
                final Date expiration = jwtProvider.getAccessClaims(accessToken).getExpiration();

                return JwtTokenResponse.builder()
                        .accessToken(accessToken)
                        .accessTokenExpiration(expiration)
                        .build();
            }
        }
        return JwtTokenResponse.builder().build();
    }

    @CircuitBreaker(name = "authServiceCircuitBreaker")
    @Bulkhead(name="authServiceBulkhead")
    @RateLimiter(name = "authServiceRateLimiter")
    @Transactional
    @Override
    public JwtResponse refresh(@NonNull String currentRefreshToken) {
        if (jwtProvider.validateRefreshToken(currentRefreshToken)) {
            final Claims currentRefreshClaims = jwtProvider.getRefreshClaims(currentRefreshToken);
            final String tokenId = currentRefreshClaims.getId();
            final String subject = currentRefreshClaims.getSubject();

            Integer accountNumber;
            try {
                accountNumber = Integer.valueOf(subject);
            } catch (NumberFormatException e) {
                throw new AuthException("Invalid token");
            }

            var savedRefreshTokenEntity = repository.findById(tokenId)
                    .orElseThrow(() -> new AuthException("Invalid token"));

            if (savedRefreshTokenEntity.getRefreshToken().equals(currentRefreshToken)) {
                final UserEntity user = userService.getByAccountNumber(accountNumber)
                        .orElseThrow(() -> new AuthException("Invalid token"));

                final JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

                if (authentication.isAuthenticated()
                        && authentication.getDetails().getRefreshTokenId().equals(tokenId)
                        && authentication.getPrincipal().getAccountNumber().equals(subject)
                ) {
                    final String appId = savedRefreshTokenEntity.getApp();
                    final String newRefreshTokenId = generateTokenId();
                    final String newAccessToken = jwtProvider.generateAccessToken(user, newRefreshTokenId);
                    final String newRefreshToken = jwtProvider.generateRefreshToken(user, newRefreshTokenId);
                    Claims newAccessClaims = jwtProvider.getAccessClaims(newAccessToken);
                    Claims newRefreshClaims = jwtProvider.getRefreshClaims(newRefreshToken);

                    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
                    refreshTokenEntity.setId(newRefreshTokenId);
                    refreshTokenEntity.setApp(appId);
                    refreshTokenEntity.setRefreshToken(newRefreshToken);
                    refreshTokenEntity.setSubject(subject);
                    refreshTokenEntity.setExpiration(newRefreshClaims.getExpiration());

                    repository.save(refreshTokenEntity);
                    repository.delete(savedRefreshTokenEntity);
                    repository.deleteAllByExpirationBeforeAndAppAndSubject(new Date(), appId, subject);

                    return JwtResponse.builder()
                            .accessToken(newAccessToken)
                            .accessTokenExpiration(newAccessClaims.getExpiration())
                            .refreshToken(newRefreshToken)
                            .refreshTokenExpiration(newRefreshClaims.getExpiration())
                            .build();
                }
            }
        }
        throw new AuthException("Invalid token");
    }

    private boolean checkApp(String appId) {
        if (appId == null) {
            return false;
        }

        if (apps == null) {
            synchronized (lockApps) {
                if (apps == null) {
                    apps = appRepository.findAll()
                            .stream()
                            .map(AppEntity::getId)
                            .collect(Collectors.toUnmodifiableSet());
                }
            }
        }

        return apps.contains(appId);
    }

    private void processRefreshTokenCountLimits(String subject, String appId) {
        repository.deleteAllByExpirationBeforeAndAppAndSubject(new Date(), appId, subject);
        if (repository.countAllBySubjectAndApp(subject, appId) > refreshTokenLimit) {
            repository.findAllBySubjectAndApp(subject, appId)
                    .stream().min(Comparator.comparingLong(value -> value.getExpiration().getTime()))
                    .ifPresent(repository::delete);
        }
    }

    private String generateTokenId() {
        return UUID.randomUUID().toString();
    }

}
