package ru.chia2k.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtDetails {
    private final String refreshTokenId;
}
