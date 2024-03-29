package dev.attara.stockify.application.service.usermanagement.updateemail;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

/**
 * A record representing a request to update a user's email.
 */
public record UpdateEmail(@NonNull String userId, @NotNull String email) { }
