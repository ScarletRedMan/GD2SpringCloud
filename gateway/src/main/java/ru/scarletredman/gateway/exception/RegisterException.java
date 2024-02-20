package ru.scarletredman.gateway.exception;

import lombok.Getter;
import ru.scarletredman.gateway.controller.response.RegisterResponse;

@Getter
public abstract class RegisterException extends RuntimeException {

    private final RegisterResponse response;

    public RegisterException(String message, RegisterResponse response) {
        super(message);
        this.response = response;
    }

    public static final class UsernameAlreadyInUse extends RegisterException {

        public UsernameAlreadyInUse() {
            super("Username is already in use", RegisterResponse.USERNAME_IS_ALREADY_IN_USE);
        }
    }

    public static final class EmailIsAlreadyInUse extends RegisterException {

        public EmailIsAlreadyInUse() {
            super("Email is already in use", RegisterResponse.EMAIL_IS_ALREADY_IN_USE);
        }
    }
}
