package dev.attara.stockify.application.security;

import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * Implementation of AuthenticatedUserProvider interface.
 */
@Component
@RequiredArgsConstructor
public class AuthenticatedUserProviderImpl implements AuthenticatedUserProvider {

    private final UserRepository userRepository;

    /**
     * Retrieves the authenticated user based on the provided principal.
     *
     * @param principal The principal representing the authenticated user.
     * @return The authenticated user.
     */
    @Override
    public User get(Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }

}
