package dev.attara.stockify.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(long userId) {
        super("User not found with ID: " + userId);
    }

}
