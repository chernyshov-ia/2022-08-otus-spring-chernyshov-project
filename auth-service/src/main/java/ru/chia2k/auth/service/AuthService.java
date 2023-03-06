package ru.chia2k.auth.service;

import jakarta.validation.Valid;
import lombok.NonNull;
import ru.chia2k.auth.dto.JwtRequest;
import ru.chia2k.auth.dto.JwtResponse;
import ru.chia2k.auth.dto.JwtTokenResponse;
import ru.chia2k.auth.dto.RefreshJwtRequest;

public interface AuthService {
    JwtResponse login(@Valid @NonNull JwtRequest authRequest);
    JwtTokenResponse getNewAccessToken(@Valid @NonNull RefreshJwtRequest request);
    JwtResponse refresh(@Valid @NonNull RefreshJwtRequest request);
}
