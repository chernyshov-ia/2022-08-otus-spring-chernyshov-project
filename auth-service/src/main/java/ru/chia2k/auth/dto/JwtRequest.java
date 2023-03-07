package ru.chia2k.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {
    @NotEmpty(message = "app должен быть указан")
    private String app;
    @NotEmpty(message = "Имя пользователя или пароль должны быть указан")
    private String login;
    @NotEmpty(message = "Имя пользователя или пароль должны быть указаны")
    private String password;
}
