package dev.attara.stockify.application.service.usermanagement.updateemail;

import org.jetbrains.annotations.NotNull;

/**
 * A record representing a request to update a user's email.
 */
public record UpdateEmail(long userId, @NotNull String email) { }
