package dev.attara.stockify.application.services.authentication;

/**
 * Represents the authentication credentials of a user.
 */
public record Authentication(
        String email,
        String password
) { }
