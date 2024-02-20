package ru.scarletredman.gateway.security;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.model.Account;

public interface AccountHolder {

    static Mono<Account> getFromSecurityContext() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (Account) context.getAuthentication().getDetails());
    }
}
