package dev.attara.stockify.application.service.usermanagement.updatepassword;

import lombok.NonNull;

/**
 * A record representing a request to update a user's password.
 */
public record UpdatePassword(@NonNull String id, @NonNull String newPassword) {
}
