package dev.attara.stockify.domain.exception;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String email) {
        super(String.format("User with email address: %s already exists", email));
    }
}
