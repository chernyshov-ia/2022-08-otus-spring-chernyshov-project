package ru.chia2k.auth.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chia2k.auth.dto.CheckAuthenticationResponse;
import ru.chia2k.security.client.authentication.JwtAuthentication;
import ru.chia2k.security.client.authentication.JwtPrincipal;

@RestController
@RequiredArgsConstructor
@Tag(name = "Check access", description = "Check authenticated.")
public class CheckRestController {

    @Operation(summary = "Check access", description = "Allow check authenticated")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("api/v1/check")
    ResponseEntity<CheckAuthenticationResponse> check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CheckAuthenticationResponse response;
        if (!authentication.isAuthenticated()) {
            response = CheckAuthenticationResponse.builder()
                    .authenticated(false)
                    .lastName("unauthenticated")
                    .build();
        } else {
            JwtPrincipal principal = ((JwtAuthentication) authentication).getPrincipal();
            response = CheckAuthenticationResponse.builder()
                    .authenticated(true)
                    .lastName(principal.getFirstName())
                    .build();
        }
        return ResponseEntity.ok(response);
    }

}
