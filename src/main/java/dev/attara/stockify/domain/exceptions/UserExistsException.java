package dev.attara.stockify.domain.exceptions;

import lombok.Getter;

/**
 * Custom exception class to represent the scenario where a user already exists.
 */
@Getter
public class UserExistsException extends RuntimeException {

    /**
     * Constructor that takes the email address of the existing user as a parameter and constructs an error message.
     *
     * @param email The email address of the existing user.
     */
    public UserExistsException(String email) {
        super("User already exists with email: " + email);
    }
}
