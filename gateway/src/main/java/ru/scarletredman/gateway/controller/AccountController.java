package ru.scarletredman.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.controller.request.GetAccountUrlResponse;
import ru.scarletredman.gateway.controller.request.LoginRequest;
import ru.scarletredman.gateway.controller.request.RegisterRequest;
import ru.scarletredman.gateway.controller.response.LoginResponse;
import ru.scarletredman.gateway.controller.response.RegisterResponse;
import ru.scarletredman.gateway.service.AccountBackupService;
import ru.scarletredman.gateway.service.AccountService;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountBackupService backupService;

    @PostMapping(value = "/accounts/registerGJAccount.php", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Mono<RegisterResponse> register(Mono<RegisterRequest> request) {
        return request.flatMap(r -> accountService.register(r.userName(), r.password(), r.email()))
                .then(Mono.just(RegisterResponse.SUCCESS));
    }

    @PostMapping(value = "/accounts/loginGJAccount.php", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Mono<LoginResponse> login(Mono<LoginRequest> request) {
        return request.flatMap(r -> accountService.auth(r.userName(), r.gjp2()))
                .map(account -> LoginResponse.success(account.getId()));
    }

    @PostMapping(value = "/getAccountURL.php", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Mono<String> accountUrl(Mono<GetAccountUrlResponse> request) {
        return backupService.getBackupServerURL();
    }
}
