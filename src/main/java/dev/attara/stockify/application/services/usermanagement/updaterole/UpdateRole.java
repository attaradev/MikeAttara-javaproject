package dev.attara.stockify.application.services.usermanagement.updaterole;

import dev.attara.stockify.domain.models.Role;
import lombok.NonNull;

/**
 * A record representing a request to update a user's role.
 */
public record UpdateRole(@NonNull String id, Role newRole) {
}
