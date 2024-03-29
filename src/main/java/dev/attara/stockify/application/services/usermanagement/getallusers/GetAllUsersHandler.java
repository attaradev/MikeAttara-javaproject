package dev.attara.stockify.application.services.usermanagement.getallusers;

import dev.attara.stockify.application.dtos.UserRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.repositories.UserRepository;
import dev.attara.stockify.infrastructure.persistence.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handler for retrieving all users.
 */
@Service
@RequiredArgsConstructor
public class GetAllUsersHandler implements ServiceHandler<GetAllUsers, List<UserRecord>> {

    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Handles the request to retrieve all users.
     *
     * @param getAllUsers The request to retrieve all users.
     * @return A list of user records representing all users.
     */
    @Override
    public List<UserRecord> handle(GetAllUsers getAllUsers) {
        return repository.findAll().stream()
                .map(mapper::mapToRecord)
                .toList();
    }
}
