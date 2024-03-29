package dev.attara.stockify.domain.exception;

/**
 * Custom exception class to represent the scenario where a user is not found.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructor that takes the user ID as a parameter and constructs an error message.
     *
     * @param userId The ID of the user that was not found.
     */
    public UserNotFoundException(long userId) {
        super("User not found with ID: " + userId);
    }

    /**
     * Constructor that takes a custom message as a parameter.
     *
     * @param message The custom error message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
