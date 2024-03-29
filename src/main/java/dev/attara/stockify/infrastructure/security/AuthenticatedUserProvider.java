package dev.attara.stockify.infrastructure.security;

import dev.attara.stockify.domain.models.User;

import java.security.Principal;

/**
 * Interface for providing authenticated user details.
 */
public interface AuthenticatedUserProvider {

    /**
     * Retrieves the authenticated user based on the provided principal.
     *
     * @param principal The principal representing the authenticated user.
     * @return The authenticated user.
     */
    User get(Principal principal);

}
