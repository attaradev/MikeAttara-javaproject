package dev.attara.stockify.application.security;

import dev.attara.stockify.application.service.usermanagement.adduser.AddUser;
import dev.attara.stockify.application.service.usermanagement.adduser.AddUserHandler;
import dev.attara.stockify.domain.model.Role;
import dev.attara.stockify.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes users during application startup if no users exist.
 */
@Component
@RequiredArgsConstructor
public class UserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final AddUserHandler addUserHandler;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            String password = "P@55w0rd";
            addUserHandler.handle(new AddUser("user@test.com", password, "Test User", Role.USER));
            addUserHandler.handle(new AddUser("admin@test.com", password, "Test Admin", Role.ADMIN));
        }
    }
}
