package ru.scarletredman.gateway.security.authentication;

import org.springframework.security.core.Authentication;
import ru.scarletredman.gateway.security.Authority;

import java.util.Collection;
import java.util.Set;

public class GuestAuthentication implements Authentication {

    @Override
    public Collection<Authority> getAuthorities() {
        return Set.of();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}

    @Override
    public String getName() {
        return "GuestAuthentication";
    }
}
