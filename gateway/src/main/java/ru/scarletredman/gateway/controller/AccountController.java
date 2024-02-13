package ru.scarletredman.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.controller.request.LoginRequest;
import ru.scarletredman.gateway.controller.request.RegisterRequest;
import ru.scarletredman.gateway.controller.response.LoginResponse;
import ru.scarletredman.gateway.controller.response.RegisterResponse;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AccountController {

    @PostMapping(value = "/accounts/registerGJAccount.php", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Mono<RegisterResponse> register(Mono<RegisterRequest> request) {
        return Mono.just(RegisterResponse.EMAIL_IS_ALREADY_IN_USE);
    }

    @PostMapping(value = "/accounts/loginGJAccount.php", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Mono<LoginResponse> login(Mono<LoginRequest> request) {
        return Mono.just(LoginResponse.error(LoginResponse.Reason.BANNED));
    }
}
