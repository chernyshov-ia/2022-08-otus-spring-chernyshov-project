package ru.chia2k.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
@Builder
public class JwtTokenResponse {
    private final String type = "bearer";
    private final String accessToken;
    private final Date accessTokenExpiration;
}
