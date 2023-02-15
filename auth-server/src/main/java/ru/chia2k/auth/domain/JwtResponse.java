package ru.chia2k.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
@Builder
public class JwtResponse {
    private final String type = "bearer";
    private final String accessToken;
    private final Date accessTokenExpiration;
    private final String refreshToken;
    private final Date refreshTokenExpiration;
}
