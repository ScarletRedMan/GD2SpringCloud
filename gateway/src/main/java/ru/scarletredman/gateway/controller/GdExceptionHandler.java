package ru.scarletredman.gateway.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.controller.response.RegisterResponse;
import ru.scarletredman.gateway.exception.RegisterException;

@RestControllerAdvice
public class GdExceptionHandler {

    @ExceptionHandler(RegisterException.class)
    Mono<RegisterResponse> registerError(RegisterException ex) {
        return Mono.just(ex.getResponse());
    }
}
