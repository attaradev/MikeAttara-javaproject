package dev.attara.stockify.application.services.usermanagement.updaterole;

import dev.attara.stockify.application.dtos.UserRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.models.Role;
import dev.attara.stockify.domain.models.User;
import dev.attara.stockify.domain.repositories.UserRepository;
import dev.attara.stockify.infrastructure.persistence.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service handler for updating a user's role.
 */
@Service
@RequiredArgsConstructor
public class UpdateRoleHandler implements ServiceHandler<UpdateRole, UserRecord> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateRoleHandler.class);

    private final UpdateRoleValidator validator;
    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Handles the request to update a user's role.
     *
     * @param updateRole The request containing the user's ID and the new role.
     * @return The updated user record.
     * @throws Exception if an error occurs during the operation.
     */
    @Override
    public UserRecord handle(UpdateRole updateRole) throws Exception {
        try {
            validator.validate(updateRole);
            String id = updateRole.id();
            Role newRole = updateRole.newRole();
            User user = repository.findById(id);
            logger.info("Updating role for user with ID: {} to {}", id, newRole);
            user.setRole(newRole);
            repository.save(user);
            logger.info("Role updated successfully for user with ID: {} to {}", id, newRole);
            return mapper.mapToRecord(user);
        } catch (Exception ex) {
            logger.error("Failed to update role for user with ID: {}", updateRole.id(), ex);
            throw ex;
        }
    }
}
