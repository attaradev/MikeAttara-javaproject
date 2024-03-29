package dev.attara.stockify.application.services.authentication;

import dev.attara.stockify.application.services.ServiceValidator;
import dev.attara.stockify.domain.services.OrderManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Validates the fields of an Authentication object.
 */
@Component
public class AuthenticationValidator implements ServiceValidator<Authentication> {

    /**
     * Validates the fields of the Authentication object.
     *
     * @param auth The authentication object to be validated.
     * @throws IllegalArgumentException If validation fails.
     */
    @Override
    public void validate(Authentication auth) {
        String email = auth.email();
        String password = auth.password();

        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Email is required");
        }

        if (OrderManager.EmailValidator.isNotValid(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Password is required");
        }
    }

}
