package ru.chia2k.auth.domain;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class JwtAuthentication implements Authentication {
    private boolean authenticated;
    private final JwtPrincipal principal;
    private final JwtDetails details;
    private final Set<? extends GrantedAuthority> authorities;

    public JwtAuthentication(JwtPrincipal principal, JwtDetails details, Set<? extends GrantedAuthority> authorities) {
        this.principal = principal;
        this.details = details;
        this.authorities = Collections.unmodifiableSet(authorities);
        this.authenticated = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override
    public Object getCredentials() { return null; }

    @Override
    public JwtDetails getDetails() { return details; }

    @Override
    public JwtPrincipal getPrincipal() { return principal; }

    @Override
    public boolean isAuthenticated() { return authenticated; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() { return principal.getFullName(); }
}
