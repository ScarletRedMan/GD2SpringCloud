package ru.scarletredman.gateway.exception;

import lombok.Getter;
import ru.scarletredman.gateway.controller.response.LoginResponse;

@Getter
public class AuthException extends RuntimeException {

    private final LoginResponse.Reason reason;

    public AuthException(LoginResponse.Reason reason) {
        this.reason = reason;
    }
}
