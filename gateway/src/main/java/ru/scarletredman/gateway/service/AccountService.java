package ru.scarletredman.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.exception.RegisterException;
import ru.scarletredman.gateway.model.Account;
import ru.scarletredman.gateway.repository.AccountRepository;

@Log4j2
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public Mono<Account> register(String username, String password, String email) {
        return Mono.just(new Account(username, password, email))
                .doOnNext(account -> { // validating
                    var usernameLen = account.getLowerUsername().length();
                    if (!account.getUsername().matches("^[aA-zZ\\d]+$")) throw new RegisterException.InvalidUsername();
                    if (usernameLen > 15) throw new RegisterException.UsernameIsTooLong();
                    if (usernameLen < 3) throw new RegisterException.TooShortUsername();

                    var passwordLen = password.length();
                    if (!password.matches("^[aA-zZ\\d]+$")) throw new RegisterException.PasswordIsInvalid();
                    if (passwordLen > 20) throw new RegisterException.PasswordIsTooLong();
                    if (passwordLen < 6) throw new RegisterException.TooShortPassword();

                    var mail = account.getEmail();
                    if (mail.length() > 50) throw new RegisterException.EmailIsInvalid();
                    if (!mail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{1,10}$")) {
                        throw new RegisterException.EmailIsInvalid();
                    }
                }).zipWith(accountRepository.getFreeIdentifier(), (account, identifier) -> {
                    account.setId(identifier);
                    return account;
                }).flatMap(accountRepository::save)
                .onErrorMap(DuplicateKeyException.class, ex -> {
                    var field = ex.getMessage().replaceAll("(.*dup key: \\{ |: \".*\" }'.*)", "");

                    if ("lowerUsername".equals(field)) {
                        return new RegisterException.UsernameAlreadyInUse();
                    }

                    if ("email".equals(field)) {
                        return new RegisterException.EmailIsAlreadyInUse();
                    }

                    return ex;
                });
    }

    public Mono<Account> find(String username) {
        return accountRepository.findAccountByLowerUsername(username);
    }
}
