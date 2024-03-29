package dev.attara.stockify.application.service.usermanagement.updatename;

import lombok.NonNull;

/**
 * A record representing a request to update a user's name.
 */
public record UpdateName(@NonNull String id, @NonNull String newName) {
}
