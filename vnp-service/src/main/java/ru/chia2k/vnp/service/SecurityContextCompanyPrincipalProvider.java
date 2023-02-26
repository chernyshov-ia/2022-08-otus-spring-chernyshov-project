package ru.chia2k.vnp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chia2k.security.client.service.CommonPrincipalProvider;
import ru.chia2k.vnp.exception.CompanyPrincipalException;
import ru.chia2k.vnp.domain.CompanyPrincipal;

@Service
@RequiredArgsConstructor
public class SecurityContextCompanyPrincipalProvider implements CompanyPrincipalProvider {
    private final CommonPrincipalProvider principalProvider;

    @Override
    public CompanyPrincipal getPrincipal() {
        var principal = principalProvider.getPrincipal();

        var builder = CompanyPrincipal.builder()
                .firstName(principal.getFirstName())
                .lastName(principal.getLastName())
                .patronymic(principal.getPatronymic())
                .email(principal.getEmail());

        if (principal.getAccountNumber() instanceof Integer) {
            return builder.accountNumber((Integer) principal.getAccountNumber()).build();
        }

        try {
            if (principal.getAccountNumber() instanceof String) {
                return builder.accountNumber(Integer.valueOf((String) principal.getAccountNumber())).build();
            }
        } catch (NumberFormatException e) {
            throw new CompanyPrincipalException("Possibly user is not company employee", e);
        }

        return null;
    }
}
