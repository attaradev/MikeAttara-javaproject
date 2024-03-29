package dev.attara.stockify.application.service.usermanagement.deleteuser;

import lombok.NonNull;

/**
 * Record representing a request to delete a user by ID.
 */
public record DeleteUser(@NonNull String id) { }
