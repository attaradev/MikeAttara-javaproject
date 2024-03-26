package dev.attara.stockify.application.security;

import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserProviderImpl implements AuthenticatedUserProvider{
    private final UserRepository userRepository;

    @Override
    public User user(Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }

}
