package ru.scarletredman.gateway.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import ru.scarletredman.gateway.security.Authority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Document(collection = "accounts")
public class Account implements UserDetails {

    @Id
    private Long id;

    private String username;

    @Indexed(unique = true)
    private String lowerUsername;

    private String password;

    @Indexed(unique = true)
    private String email;

    private boolean banned = false;

    private String salt;

    private Set<Authority> authorities = new HashSet<>();

    public Account() {}

    public Account(String username, String password, String email) {
        this.username = username;
        this.lowerUsername = username.toLowerCase();
        this.password = password;
        this.email = email.toLowerCase();
    }

    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // TODO: check banned
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
