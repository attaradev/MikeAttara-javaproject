package dev.attara.stockify.application.service.usermanagement.updateemail;

import dev.attara.stockify.application.service.ServiceValidator;
import dev.attara.stockify.application.util.EmailValidator;
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
        if (EmailValidator.isNotValid(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
