package ru.chia2k.security.client.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;

public interface JwtValidator {
    boolean validateAccessToken(@NonNull String accessToken);
    Claims getAccessClaims(@NonNull String token);
}
