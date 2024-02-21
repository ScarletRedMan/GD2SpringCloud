package ru.scarletredman.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.controller.response.AuthResponse;
import ru.scarletredman.gateway.exception.AuthException;
import ru.scarletredman.gateway.service.AccountService;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @GetMapping("/auth")
    Mono<ResponseEntity<AuthResponse>> auth(
            @RequestHeader("GD2S-AccountId") long accountId,
            @RequestHeader("GD2S-GJP2") String gjp2
    ) {
        return accountService.auth(accountId, gjp2)
                .onErrorResume(AuthException.class, ex -> Mono.empty())
                .singleOptional()
                .map(optional -> {
                    if (optional.isEmpty()) return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).build();

                    var account = optional.get();
                    return ResponseEntity.ok(AuthResponse.of(account));
                });
    }
}
