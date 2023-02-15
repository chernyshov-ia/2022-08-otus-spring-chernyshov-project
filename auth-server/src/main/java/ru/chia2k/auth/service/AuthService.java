package ru.chia2k.auth.service;

import lombok.NonNull;
import ru.chia2k.auth.domain.*;

public interface AuthService {
    JwtResponse login(@NonNull JwtRequest authRequest);
    JwtTokenResponse getNewAccessToken(@NonNull String refreshToken);
    JwtResponse refresh(@NonNull String refreshToken);
    // JwtAuthentication getAuthInfo();
}
