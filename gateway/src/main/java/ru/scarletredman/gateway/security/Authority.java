package ru.scarletredman.gateway.security;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
