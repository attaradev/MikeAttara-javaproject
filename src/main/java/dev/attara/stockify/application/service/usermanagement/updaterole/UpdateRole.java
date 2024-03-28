package dev.attara.stockify.application.service.usermanagement.updaterole;

import dev.attara.stockify.domain.model.Role;

/**
 * A record representing a request to update a user's role.
 */
public record UpdateRole(long id, Role newRole) {
}
