package ru.scarletredman.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.model.Account;
import ru.scarletredman.gateway.repository.AccountRepository;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public Mono<Account> register(String username, String password, String email) {
        var account = new Account(username, password, email);
        return accountRepository.save(account);
    }

    public Mono<Account> find(String username) {
        return accountRepository.findAccountByLowerUsername(username);
    }
}
