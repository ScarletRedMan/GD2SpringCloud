package ru.scarletredman.gateway.security.authentication;

import org.springframework.security.core.Authentication;
import ru.scarletredman.gateway.model.Account;
import ru.scarletredman.gateway.security.Authority;

import java.util.Collection;

public class Gjp2Authentication implements Authentication {

    private final Account account;

    public Gjp2Authentication(Account account) {
        this.account = account;
    }

    @Override
    public Collection<Authority> getAuthorities() {
        return account.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return account.getPassword();
    }

    @Override
    public Object getDetails() {
        return account;
    }

    @Override
    public Object getPrincipal() {
        return account.getId();
    }

    @Override
    public boolean isAuthenticated() {
        return !account.isBanned();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}

    @Override
    public String getName() {
        return "Gjp2Authentication";
    }
}
