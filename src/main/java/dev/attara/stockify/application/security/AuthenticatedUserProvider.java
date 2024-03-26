package dev.attara.stockify.application.security;

import dev.attara.stockify.domain.model.User;

import java.security.Principal;

public interface AuthenticatedUserProvider {
    User user(Principal principal);
}
