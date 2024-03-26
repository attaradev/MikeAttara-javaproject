package dev.attara.stockify.application.dto.output;

import dev.attara.stockify.domain.model.Role;


public record UserRecord(
        Long id,
        String name,
        String email,
        Role role
) {
}
