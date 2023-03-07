package ru.chia2k.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {
    @NotEmpty(message = "refreshToken должен быть указан")
    private String refreshToken;
}
