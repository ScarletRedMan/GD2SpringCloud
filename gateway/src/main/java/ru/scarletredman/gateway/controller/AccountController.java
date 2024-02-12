package ru.scarletredman.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.controller.request.RegisterRequest;
import ru.scarletredman.gateway.controller.response.RegisterResponse;

@RequiredArgsConstructor
@RestController
public class AccountController {

    @PostMapping(value = "/accounts/registerGJAccount.php", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Mono<RegisterResponse> register(RegisterRequest request) {
        return Mono.just(RegisterResponse.EMAIL_IS_ALREADY_IN_USE); // TODO
    }
}
