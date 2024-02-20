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

    public static final class InvalidUsername extends RegisterException {

        public InvalidUsername() {
            super("Invalid username", RegisterResponse.INVALID_USERNAME);
        }
    }

    public static final class UsernameIsTooLong extends RegisterException {

        public UsernameIsTooLong() {
            super("Username is too long", RegisterResponse.INVALID_USERNAME);
        }
    }

    public static final class TooShortUsername extends RegisterException {

        public TooShortUsername() {
            super("Too short username", RegisterResponse.TOO_SHORT_USERNAME);
        }
    }

    public static final class PasswordIsInvalid extends RegisterException {

        public PasswordIsInvalid() {
            super("Password is invalid", RegisterResponse.PASSWORD_IS_INVALID);
        }
    }

    public static final class PasswordIsTooLong extends RegisterException {

        public PasswordIsTooLong() {
            super("Password is too long", RegisterResponse.PASSWORD_IS_INVALID);
        }
    }

    public static final class TooShortPassword extends RegisterException {

        public TooShortPassword() {
            super("Too short password", RegisterResponse.TOO_SHORT_PASSWORD);
        }
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

    public static final class EmailIsInvalid extends RegisterException {

        public EmailIsInvalid() {
            super("Email is invalid", RegisterResponse.EMAIL_IS_INVALID);
        }
    }
}
