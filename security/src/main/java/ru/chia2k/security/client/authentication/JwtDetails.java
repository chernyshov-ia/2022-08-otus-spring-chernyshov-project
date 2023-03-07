package ru.chia2k.security.client.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtDetails {
    private final String refreshTokenId;
}
