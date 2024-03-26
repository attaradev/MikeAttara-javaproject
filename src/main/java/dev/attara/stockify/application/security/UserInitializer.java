package dev.attara.stockify.application.security;

import dev.attara.stockify.application.dto.input.CreateUserDTO;
import dev.attara.stockify.application.service.UserService;
import dev.attara.stockify.domain.model.Role;
import dev.attara.stockify.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            String password = "P@55w0rd";
            userService.createUser(new CreateUserDTO("admin@test.com", password, "Admin User", Role.ADMIN));
            userService.createUser(new CreateUserDTO("user@test.com", password, null, null));
        }
    }
}
