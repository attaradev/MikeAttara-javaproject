package dev.attara.stockify.application.dtos;

import dev.attara.stockify.domain.models.Role;

/**
 * A record representing a user, including their ID, name, email, and role.
 */
public record UserRecord(
        String id,

        String name,

        String email,

        Role role
) { }
