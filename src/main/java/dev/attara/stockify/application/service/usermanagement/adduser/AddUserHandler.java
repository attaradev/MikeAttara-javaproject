package dev.attara.stockify.application.service.usermanagement.adduser;

import dev.attara.stockify.application.dto.UserRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Role;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.UserRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service handler for adding a new user.
 */
@Service
@RequiredArgsConstructor
public class AddUserHandler implements ServiceHandler<AddUser, UserRecord> {

    private static final Logger logger = LoggerFactory.getLogger(AddUserHandler.class);

    private final UserRepository repository;
    private final AddUserValidator validator;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Handles the request to add a new user.
     *
     * @param addUser The request to add a new user.
     * @return The user record of the newly created user.
     * @throws RuntimeException if there is an error while creating the user.
     */
    @Override
    public UserRecord handle(AddUser addUser) {
        validator.validate(addUser);

        try {
            String email = addUser.email();
            String encodedPassword = passwordEncoder.encode(addUser.password());
            String name = addUser.name();
            Role role = addUser.role() != null ? addUser.role() : Role.USER;
            long id = repository.nextId();

            User user = User.create(id, email, encodedPassword, name, role);
            repository.save(user);

            return mapper.mapToRecord(user);
        } catch (Exception e) {
            logger.error("Failed to create user", e); // Log full stack trace
            throw new RuntimeException("Failed to create user", e);
        }
    }
}
