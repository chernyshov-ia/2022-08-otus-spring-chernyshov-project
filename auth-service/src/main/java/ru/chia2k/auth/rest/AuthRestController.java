package ru.chia2k.auth.rest;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chia2k.auth.dto.JwtRequest;
import ru.chia2k.auth.dto.JwtResponse;
import ru.chia2k.auth.dto.JwtTokenResponse;
import ru.chia2k.auth.dto.RefreshJwtRequest;
import ru.chia2k.auth.service.AuthServiceImpl;

import java.util.concurrent.TimeoutException;


@RestController
@RequiredArgsConstructor
@Tag(name = "Authenticate", description = "The Authenticate API. Contains all operations for obtaining an access tokens.")
public class AuthRestController {
    private final AuthServiceImpl authService;

    @Operation(summary = "Login and get new access & refresh tokens", description = "Login and get new access & refresh tokens")
    @PostMapping("api/v1/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Get new access token", description = "Get new access token")
    @PostMapping("api/v1/token")
    public ResponseEntity<JwtTokenResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtTokenResponse token = authService.getNewAccessToken(request);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Get new refresh token", description = "Get new refresh token")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("api/v1/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.refresh(request);
        return ResponseEntity.ok(token);
    }

    @ExceptionHandler({CallNotPermittedException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleCallNotPermittedException() {
    }

    @ExceptionHandler({TimeoutException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public void handleTimeoutException() {
    }

    @ExceptionHandler({ RequestNotPermitted.class })
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public void handleRequestNotPermitted() {
    }

}
