package dev.attara.stockify.application.dto.input;

import dev.attara.stockify.domain.model.Role;

public record CreateUserDTO(

        String email,

        String password,

        String name,

        Role role

) { }
