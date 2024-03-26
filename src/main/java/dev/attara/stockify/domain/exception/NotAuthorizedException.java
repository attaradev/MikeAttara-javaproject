package dev.attara.stockify.domain.exception;

/**
 * Exception thrown when a user is not authorized to perform a specific action.
 */
public class NotAuthorizedException extends RuntimeException {

    /**
     * Constructs a NotAuthorizedException with a default message.
     */
    public NotAuthorizedException() {
        super("Not authorized to perform this action");
    }

}
