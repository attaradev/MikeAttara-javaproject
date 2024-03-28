package dev.attara.stockify.application.service.usermanagement.updatepassword;

import dev.attara.stockify.application.dto.UserRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.UserRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service handler for updating a user's password.
 */
@Service
@RequiredArgsConstructor
public class UpdatePasswordHandler implements ServiceHandler<UpdatePassword, UserRecord> {

    private final UpdatePasswordValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Handles the request to update a user's password.
     *
     * @param updatePassword The request containing the user's ID and the new password.
     * @return The updated user record after the password update.
     */
    @Override
    public UserRecord handle(UpdatePassword updatePassword) {
        validator.validate(updatePassword);
        long id = updatePassword.id();
        String newPassword = passwordEncoder.encode(updatePassword.newPassword());
        User user = repository.findById(id);
        user.setPassword(newPassword);
        repository.save(user);
        return mapper.mapToRecord(user);
    }

}
