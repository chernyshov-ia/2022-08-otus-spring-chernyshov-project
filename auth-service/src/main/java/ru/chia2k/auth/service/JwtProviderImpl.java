package ru.chia2k.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.chia2k.auth.entity.UserEntity;
import ru.chia2k.security.client.service.JwtTokenClaims;
import ru.chia2k.security.client.service.JwtUtils;
import ru.chia2k.security.client.service.JwtValidatorImpl;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class JwtProviderImpl extends JwtValidatorImpl implements JwtProvider{
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final Integer jwtAccessTokenLifetimeSeconds;
    private final Integer jwtRefreshTokenLifetimeSeconds;

    public JwtProviderImpl(
            @Value("${application.jwt.secret.access}") String jwtAccessSecret,
            @Value("${application.jwt.secret.refresh}") String jwtRefreshSecret,
            @Value("${application.jwt.access-token-lifetime-seconds:300}") Integer jwtAccessTokenLifetimeSeconds,
            @Value("${application.jwt.refresh-token-lifetime-days:30}") Integer jwtRefreshTokenLifetimeDays
    ) {
        super(jwtAccessSecret);
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.jwtAccessTokenLifetimeSeconds = jwtAccessTokenLifetimeSeconds;
        this.jwtRefreshTokenLifetimeSeconds = jwtRefreshTokenLifetimeDays * 24 * 3600;
    }

    public String generateAccessToken(@NonNull UserEntity user, @NonNull String refreshTokenId) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now
                .plusSeconds(jwtAccessTokenLifetimeSeconds)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        final List<String> authorities = user.getAuthorities().stream()
                .map(a -> a.getId())
                .toList();

        return Jwts.builder()
                .setSubject(String.valueOf(user.getAccountNumber()))
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim(JwtTokenClaims.REFRESH_TOKEN_ID.getName(), refreshTokenId)
                .claim(JwtTokenClaims.AUTHORITIES.getName(), authorities)
                .claim(JwtTokenClaims.FIRST_NAME.getName(), user.getFirstName())
                .claim(JwtTokenClaims.LAST_NAME.getName(), user.getLastName())
                .claim(JwtTokenClaims.PATRONYMIC.getName(), user.getPatronymic())
                .claim(JwtTokenClaims.EMAIL.getName(), user.getEmail())
                .claim(JwtTokenClaims.POSITION_NAME.getName(), user.getPositionName())
                .compact();
    }

    public String generateRefreshToken(@NonNull UserEntity user, @NonNull String tokenId) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now
                .plusSeconds(jwtRefreshTokenLifetimeSeconds)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getAccountNumber()))
                .setExpiration(refreshExpiration)
                .setId(tokenId)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return JwtUtils.getClaims(token, jwtRefreshSecret);
    }
}
