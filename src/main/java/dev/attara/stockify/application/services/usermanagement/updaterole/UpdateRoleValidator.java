package dev.attara.stockify.application.services.usermanagement.updaterole;

import dev.attara.stockify.application.services.ServiceValidator;
import org.springframework.stereotype.Component;

/**
 * Validator for validating the UpdateRole request.
 */
@Component
public class UpdateRoleValidator implements ServiceValidator<UpdateRole> {

    /**
     * Validates the UpdateRole request.
     *
     * @param updateRole The request to validate.
     * @throws IllegalArgumentException if the new role is null.
     */
    @Override
    public void validate(UpdateRole updateRole) throws IllegalArgumentException {
        if (updateRole.newRole() == null) {
            throw new IllegalArgumentException("newRole required");
        }
    }
}
