package ru.chia2k.auth.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class CheckAuthenticationResponse {
    private final boolean authenticated;
    private final String lastName;
}
