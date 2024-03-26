package dev.attara.stockify.application.service.authentication;

/**
 * Represents the authentication credentials of a user.
 */
public record Authentication(
        String email,
        String password
) { }
