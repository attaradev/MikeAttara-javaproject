package dev.attara.stockify.application.services.usermanagement.deleteuser;

import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.models.User;
import dev.attara.stockify.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service handler for deleting a user.
 */
@Service
@RequiredArgsConstructor
public class DeleteUserHandler implements ServiceHandler<DeleteUser, Boolean> {

    private final UserRepository repository;

    /**
     * Handles the request to delete a user.
     *
     * @param deleteUser The request to delete a user.
     * @return True if the user was successfully deleted, false otherwise.
     */
    @Override
    @Transactional
    public Boolean handle(DeleteUser deleteUser) {
        User user = repository.findById(deleteUser.id());
        if (user != null) {
            repository.delete(user);
            return true;
        }
        return false;
    }
}
