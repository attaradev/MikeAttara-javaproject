package dev.attara.stockify.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long userId) {
        super("User not found with ID: " + userId);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
