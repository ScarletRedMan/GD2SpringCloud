package ru.scarletredman.gateway.controller.response;

import ru.scarletredman.gateway.model.Account;
import java.util.Set;
import java.util.stream.Collectors;

public record AuthResponse(long id, String username, Set<String> authorities) {

    public static AuthResponse of(Account account) {
        return new AuthResponse(
                account.getId(),
                account.getUsername(),
                account.getAuthorities().stream().map(Enum::name).collect(Collectors.toSet())
        );
    }
}
