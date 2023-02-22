package ru.chia2k.security.client.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.chia2k.security.client.authentication.JwtAuthentication;
import ru.chia2k.security.client.authentication.JwtDetails;
import ru.chia2k.security.client.authentication.JwtPrincipal;

import java.security.Key;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generateAuthentication(Claims claims) {
        var principal = JwtPrincipal.builder()
                .accountNumber(claims.getSubject())
                .firstName(claims.get(JwtTokenClaims.FIRST_NAME.getName(), String.class))
                .lastName(claims.get(JwtTokenClaims.LAST_NAME.getName(), String.class))
                .patronymic(claims.get(JwtTokenClaims.PATRONYMIC.getName(), String.class))
                .email(claims.get(JwtTokenClaims.EMAIL.getName(), String.class))
                .positionCode(claims.get(JwtTokenClaims.POSITION_CODE.getName(), String.class))
                .positionName(claims.get(JwtTokenClaims.POSITION_NAME.getName(), String.class))
                .build();

        var authorities = getAuthorities(claims);
        var details = new JwtDetails(claims.get(JwtTokenClaims.REFRESH_TOKEN_ID.getName(), String.class));
        return new JwtAuthentication(principal, details, authorities);
    }

    private static Set<? extends GrantedAuthority> getAuthorities(Claims claims) {
        final List<String> roles = claims.get(JwtTokenClaims.AUTHORITIES.getName(), List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public static Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
