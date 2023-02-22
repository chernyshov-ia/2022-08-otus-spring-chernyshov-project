package ru.chia2k.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {
    private String app;
    private String login;
    private String password;
}
