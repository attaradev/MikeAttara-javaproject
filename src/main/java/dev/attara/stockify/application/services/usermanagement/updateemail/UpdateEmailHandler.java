package dev.attara.stockify.application.services.usermanagement.updateemail;

import dev.attara.stockify.application.dtos.UserRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.exceptions.UserExistsException;
import dev.attara.stockify.domain.models.User;
import dev.attara.stockify.domain.repositories.UserRepository;
import dev.attara.stockify.infrastructure.persistence.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handler for updating a user's email.
 */
@Service
@RequiredArgsConstructor
public class UpdateEmailHandler implements ServiceHandler<UpdateEmail, UserRecord> {

    private final UserMapper mapper;
    private final UserRepository repository;

    /**
     * Handles the request to update a user's email.
     *
     * @param updateEmail The request containing the user ID and the new email.
     * @return The updated user record after the email update.
     * @throws UserExistsException if the new email already exists in the system.
     */
    @Override
    public UserRecord handle(UpdateEmail updateEmail) {
        String userId = updateEmail.userId();
        String newEmail = updateEmail.email();

        if (repository.userEmailExists(newEmail)) {
            throw new UserExistsException(newEmail);
        }

        User user = repository.findById(userId);
        user.setEmail(newEmail);
        repository.save(user);
        return mapper.mapToRecord(user);
    }
}
