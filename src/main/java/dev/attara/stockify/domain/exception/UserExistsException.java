package dev.attara.stockify.domain.exception;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String email) {
        super("User exist with email: " + email);
    }
}
