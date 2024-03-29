package dev.attara.stockify.application.services.usermanagement.getuser;

import dev.attara.stockify.application.dtos.UserRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.models.User;
import dev.attara.stockify.domain.repositories.UserRepository;
import dev.attara.stockify.infrastructure.persistence.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handler for retrieving a user by ID.
 */
@Service
@RequiredArgsConstructor
public class GetUserHandler implements ServiceHandler<GetUser, UserRecord> {

    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Handles the request to retrieve a user by ID.
     *
     * @param getUser The request containing the ID of the user to retrieve.
     * @return The user record corresponding to the provided ID.
     */
    @Override
    public UserRecord handle(GetUser getUser) {
        User user = repository.findById(getUser.id());
        return mapper.mapToRecord(user);
    }
}
