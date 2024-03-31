package dev.attara.stockify.application.services.ordermanagement.getordersbyuser;

import dev.attara.stockify.application.services.ServiceValidator;
import dev.attara.stockify.domain.exceptions.NotAuthorizedException;
import dev.attara.stockify.domain.models.User;
import org.springframework.stereotype.Component;

/**
 * Validator for validating the service request to retrieve orders by user ID.
 */
@Component
public class GetOrdersByUserValidator implements ServiceValidator<GetOrdersByUser> {
    /**
     * Validates the service request.
     *
     * @param getOrdersByUser The service request to validate.
     * @throws NotAuthorizedException If the validation fails due to unauthorized access.
     */
    @Override
    public void validate(GetOrdersByUser getOrdersByUser) throws NotAuthorizedException {
        User user = getOrdersByUser.user();
        if (user.isNotAdmin() && !user.getId().equals(getOrdersByUser.userId())) {
            throw new NotAuthorizedException();
        }
    }
}
