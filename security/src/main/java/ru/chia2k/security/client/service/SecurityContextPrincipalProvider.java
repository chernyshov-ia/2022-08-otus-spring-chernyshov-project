package ru.chia2k.security.client.service;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.chia2k.security.client.authentication.JwtPrincipal;
import ru.chia2k.security.client.principal.CommonPrincipal;

public class SecurityContextPrincipalProvider implements CommonPrincipalProvider {
    @Override
    public CommonPrincipal getPrincipal() {
        return (JwtPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
