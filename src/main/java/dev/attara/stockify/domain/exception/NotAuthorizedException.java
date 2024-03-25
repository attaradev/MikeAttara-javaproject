package dev.attara.stockify.domain.exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super("Not authorized to perform this action");
    }
}

