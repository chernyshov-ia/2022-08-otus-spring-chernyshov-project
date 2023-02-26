package ru.chia2k.vnp.service;

import ru.chia2k.security.client.service.CommonPrincipalProvider;
import ru.chia2k.vnp.domain.CompanyPrincipal;

public interface CompanyPrincipalProvider extends CommonPrincipalProvider {
    CompanyPrincipal getPrincipal();
}
