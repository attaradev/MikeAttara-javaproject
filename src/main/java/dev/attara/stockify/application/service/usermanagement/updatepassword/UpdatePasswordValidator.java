package dev.attara.stockify.application.service.usermanagement.updatepassword;

import dev.attara.stockify.application.service.ServiceValidator;
import org.springframework.stereotype.Component;

/**
 * Validator for validating requests to update a user's password.
 */
@Component
public class UpdatePasswordValidator implements ServiceValidator<UpdatePassword> {

    /**
     * Validates the update password request.
     *
     * @param updatePassword The request to update the user's password.
     * @throws IllegalArgumentException If the new password is empty.
     */
    @Override
    public void validate(UpdatePassword updatePassword) {
        if (updatePassword.newPassword().isEmpty()) {
            throw new IllegalArgumentException("newPassword required");
        }
    }

}
