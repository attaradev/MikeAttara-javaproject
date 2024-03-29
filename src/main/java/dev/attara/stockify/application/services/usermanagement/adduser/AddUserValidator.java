package dev.attara.stockify.application.services.usermanagement.adduser;

import dev.attara.stockify.application.services.ServiceValidator;
import dev.attara.stockify.domain.services.OrderManager;
import dev.attara.stockify.domain.exceptions.UserExistsException;
import dev.attara.stockify.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validator for the AddUser operation.
 */
@Component
@RequiredArgsConstructor
public class AddUserValidator implements ServiceValidator<AddUser> {

    private final UserRepository userRepository;

    /**
     * Validates the AddUser request.
     *
     * @param addUser The AddUser request to validate.
     * @throws IllegalArgumentException If the email is empty or in an invalid format.
     * @throws UserExistsException      If a user with the same email already exists.
     */
    @Override
    public void validate(AddUser addUser) throws IllegalArgumentException, UserExistsException {
        if (addUser.email() == null || addUser.email().isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (OrderManager.EmailValidator.isNotValid(addUser.email())) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (userRepository.userEmailExists(addUser.email())) {
            throw new UserExistsException(addUser.email());
        }
    }
}
