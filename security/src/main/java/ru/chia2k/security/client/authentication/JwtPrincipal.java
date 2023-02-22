package ru.chia2k.security.client.authentication;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Builder
@Getter
public class JwtPrincipal {
    private final String accountNumber;
    private final String firstName;
    private final String lastName;
    private final String patronymic;
    private final String email;
    private final String positionCode;
    private final String positionName;
    public String getFullName() {
        return lastName + " " + firstName + patronymic!=null?(" " + patronymic):"";
    }
}
