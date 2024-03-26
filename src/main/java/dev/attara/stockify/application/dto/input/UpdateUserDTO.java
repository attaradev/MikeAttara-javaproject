package dev.attara.stockify.application.dto.input;

import dev.attara.stockify.domain.model.Role;

public record UpdateUserDTO(

        String name,

        String email,

        String password,

        Role role
) { }
