package ru.chia2k.auth.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import ru.chia2k.auth.domain.UserEntity;

public interface JwtProvider {
    String generateAccessToken(@NonNull UserEntity user, @NonNull String tokenId);
    String generateRefreshToken(@NonNull UserEntity user, @NonNull String tokenId);
    boolean validateAccessToken(@NonNull String accessToken);
    boolean validateRefreshToken(@NonNull String refreshToken);
    Claims getAccessClaims(@NonNull String token);
    Claims getRefreshClaims(@NonNull String token);
}
