package ru.chia2k.security.client.authentication;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.chia2k.security.client.principal.CommonPrincipal;


@RequiredArgsConstructor
@Builder
@Getter
public class JwtPrincipal implements CommonPrincipal {
    private final String accountNumber;
    private final String firstName;
    private final String lastName;
    private final String patronymic;
    private final String email;
    private final String positionCode;
    private final String positionName;

    public String getFullName() {
        return lastName + " " + firstName + (patronymic != null ? (" " + patronymic) : "");
    }
}
