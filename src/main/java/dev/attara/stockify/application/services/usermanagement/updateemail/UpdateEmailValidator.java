package dev.attara.stockify.application.services.usermanagement.updateemail;

import dev.attara.stockify.application.services.ServiceValidator;
import dev.attara.stockify.domain.services.OrderManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Validator for validating the email format in an UpdateEmail request.
 */
@Slf4j
@Component
public class UpdateEmailValidator implements ServiceValidator<UpdateEmail> {

    /**
     * Validates the email format in an UpdateEmail request.
     *
     * @param updateEmail The request to update the email.
     * @throws IllegalArgumentException if the email format is invalid.
     */
    @Override
    public void validate(UpdateEmail updateEmail) throws IllegalArgumentException {
        String email = updateEmail.email();
        if (OrderManager.EmailValidator.isNotValid(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
