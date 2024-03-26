package dev.attara.stockify.domain.exception;

import lombok.Getter;

@Getter
public class UserExistsException extends RuntimeException {

    public UserExistsException(String email) {
        super("User already exists with email: " + email);
    }

}
