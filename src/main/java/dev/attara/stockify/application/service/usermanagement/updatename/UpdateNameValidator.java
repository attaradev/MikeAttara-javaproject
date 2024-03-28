package dev.attara.stockify.application.service.usermanagement.updatename;

import dev.attara.stockify.application.service.ServiceValidator;
import org.springframework.stereotype.Component;

/**
 * Validator for validating the update name request.
 */
@Component
public class UpdateNameValidator implements ServiceValidator<UpdateName> {

    /**
     * Validates the update name request.
     *
     * @param updateName The update name request to validate.
     * @throws IllegalArgumentException If the new name is empty.
     */
    @Override
    public void validate(UpdateName updateName) throws IllegalArgumentException {
        String newName = updateName.newName();
        if (newName.isEmpty()) {
            throw new IllegalArgumentException("newName required");
        }
    }

}
