package dev.attara.stockify.application.service.usermanagement.updatename;

import dev.attara.stockify.application.dto.UserRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.UserRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handler for updating a user's name.
 */
@Service
@RequiredArgsConstructor
public class UpdateNameHandler implements ServiceHandler<UpdateName, UserRecord> {

    private final UserRepository repository;
    private final UpdateNameValidator validator;
    private final UserMapper mapper;

    /**
     * Handles the request to update a user's name.
     *
     * @param updateName The request containing the user's ID and the new name.
     * @return The updated user record after the name update.
     */
    @Override
    public UserRecord handle(UpdateName updateName) {
        validator.validate(updateName);
        long id = updateName.id();
        String newName = updateName.newName();
        User user = repository.findById(id);
        user.setName(newName);
        repository.save(user);
        return mapper.mapToRecord(user);
    }
}
