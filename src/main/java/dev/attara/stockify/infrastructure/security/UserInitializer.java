package dev.attara.stockify.infrastructure.security;

import dev.attara.stockify.domain.model.Role;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            Long id = userRepository.nextId();
            String name = "admin";
            String email = "admin@test.com";
            String password = passwordEncoder.encode("P@55w0rd");
            Role role = Role.ADMIN;
            User user = new User(id, name, email, password, role);
            userRepository.save(user);
        }
    }
}
