package dev.attara.stockify.application.dto.input;

import dev.attara.stockify.domain.model.Role;
import org.jetbrains.annotations.NotNull;

public record CreateUserDTO(
        String email,
        String password,
        String name,
        Role role
) {
}
