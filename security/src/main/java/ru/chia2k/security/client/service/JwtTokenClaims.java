package ru.chia2k.security.client.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JwtTokenClaims {
    AUTHORITIES("roles"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    PATRONYMIC("patronymic"),
    EMAIL("email"),
    POSITION_NAME("posName"),
    POSITION_CODE("posCode"),
    REFRESH_TOKEN_ID("refId");
    private final String name;
}
