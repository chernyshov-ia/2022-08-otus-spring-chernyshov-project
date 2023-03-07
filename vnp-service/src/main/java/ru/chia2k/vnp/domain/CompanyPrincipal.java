package ru.chia2k.vnp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.chia2k.security.client.principal.CommonPrincipal;

@RequiredArgsConstructor
@Getter
@Builder
public class CompanyPrincipal implements CommonPrincipal {
    private final Integer accountNumber;
    private final String firstName;
    private final String lastName;
    private final String patronymic;
    private final String email;

    public String getFullName() {
        return lastName + " " + firstName + (patronymic != null ? (" " + patronymic) : "");
    }
}
