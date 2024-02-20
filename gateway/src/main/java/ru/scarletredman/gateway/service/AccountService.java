package ru.scarletredman.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.controller.response.LoginResponse;
import ru.scarletredman.gateway.exception.AuthException;
import ru.scarletredman.gateway.exception.RegisterException;
import ru.scarletredman.gateway.model.Account;
import ru.scarletredman.gateway.repository.AccountRepository;
import ru.scarletredman.gateway.security.GJP2;

@Log4j2
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final GJP2 gjp2;

    public Mono<Account> register(String username, String password, String email) {
        return Mono.just(new Account(username, gjp2.hash(password), email))
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

    public Mono<Account> auth(String username, String gjp2) {
        return accountRepository.findAccountByLowerUsername(username)
                .singleOptional()
                .map(optional -> optional.orElseThrow(() -> new AuthException(LoginResponse.Reason.LOGIN_FAILED)))
                .doOnNext(account -> {
                    if (account.isBanned()) {
                        throw new AuthException(LoginResponse.Reason.BANNED);
                    }

                    if (account.getPassword().equals(gjp2)) return;

                    throw new AuthException(LoginResponse.Reason.LOGIN_FAILED);
                });
    }

    public Mono<Account> find(String username) {
        return accountRepository.findAccountByLowerUsername(username);
    }
}
