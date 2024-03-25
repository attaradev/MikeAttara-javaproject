package dev.attara.stockify.application.dto.input;

import dev.attara.stockify.domain.model.Role;
import org.jetbrains.annotations.NotNull;

public record CreateUserDTO(
        @NotNull String name,
        @NotNull String email,
        @NotNull String password,
        Role role
) {
    public CreateUserDTO {
        if (role == null) {
            role = Role.USER;
        }
    }
}
