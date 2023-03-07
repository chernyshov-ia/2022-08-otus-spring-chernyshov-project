package ru.chia2k.auth.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import ru.chia2k.auth.entity.UserEntity;
import ru.chia2k.security.client.service.JwtValidator;

public interface JwtProvider extends JwtValidator {
    String generateAccessToken(@NonNull UserEntity user, @NonNull String tokenId);
    String generateRefreshToken(@NonNull UserEntity user, @NonNull String tokenId);
    boolean validateRefreshToken(@NonNull String refreshToken);
    Claims getRefreshClaims(@NonNull String token);
}
