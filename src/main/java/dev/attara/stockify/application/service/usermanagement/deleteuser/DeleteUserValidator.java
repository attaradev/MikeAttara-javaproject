package dev.attara.stockify.application.service.usermanagement.deleteuser;

import dev.attara.stockify.application.service.ServiceValidator;
import org.springframework.stereotype.Component;

/**
 * Validator for the DeleteUser service.
 */
@Component
public class DeleteUserValidator implements ServiceValidator<DeleteUser> {

    /**
     * Validates the DeleteUser request.
     *
     * @param deleteUser The request to delete a user.
     * @throws IllegalArgumentException if the user ID is not positive.
     */
    @Override
    public void validate(DeleteUser deleteUser) throws IllegalArgumentException {
        if (deleteUser.id() <= 0) {
            throw new IllegalArgumentException("User ID must be positive.");
        }
    }
}
