package dev.attara.stockify.application.dto;

import dev.attara.stockify.domain.model.Role;

/**
 * A record representing a user, including their ID, name, email, and role.
 */
public record UserRecord(
        long id,

        String name,

        String email,

        Role role
) { }
