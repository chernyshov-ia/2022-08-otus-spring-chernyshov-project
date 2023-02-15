package ru.chia2k.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.chia2k.auth.domain.JwtRequest;
import ru.chia2k.auth.domain.JwtResponse;
import ru.chia2k.auth.domain.JwtTokenResponse;
import ru.chia2k.auth.domain.RefreshJwtRequest;
import ru.chia2k.auth.service.AuthService;



@RestController
@RequiredArgsConstructor
@Tag(name = "Authenticate", description = "The Authenticate API. Contains all operations for obtaining an access tokens.")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Login and get new access & refresh tokens", description = "Login and get new access & refresh tokens")
    @PostMapping("api/v1/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Get new access token", description = "Get new access token")
    @PostMapping("api/v1/token")
    public ResponseEntity<JwtTokenResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtTokenResponse token = authService.getNewAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Get new refresh token", description = "Get new refresh token")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("api/v1/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}
