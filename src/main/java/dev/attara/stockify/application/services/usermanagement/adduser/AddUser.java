package dev.attara.stockify.application.services.usermanagement.adduser;

import dev.attara.stockify.domain.models.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Represents a request to add a new user.
 */
public record AddUser(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        String password,

        @NotBlank(message = "Name cannot be blank")
        String name,

        Role role
) { }
