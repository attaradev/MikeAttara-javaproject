package dev.attara.stockify.application.service.ordermanagement.createorder;

import dev.attara.stockify.application.service.ServiceValidator;
import dev.attara.stockify.domain.exception.NotAuthorizedException;
import dev.attara.stockify.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Validator for validating the data required to create an order.
 */
@Component
public class CreateOrderValidator implements ServiceValidator<CreateOrder> {

    /**
     * Validates the data required to create an order.
     *
     * @param createOrder The data representing the order to be created.
     * @throws NotAuthorizedException   If the user is not authorized to perform the action.
     * @throws IllegalArgumentException If the provided data is invalid.
     */
    @Override
    public void validate(CreateOrder createOrder) throws NotAuthorizedException, IllegalArgumentException {
        User user = createOrder.user();
        List<ProductLineData> productLines = createOrder.productLines();

        // Check if the user is authorized
        if (user == null) {
            throw new NotAuthorizedException();
        }

        // Check if any products are selected
        if (productLines.isEmpty()) {
            throw new IllegalArgumentException("No products selected");
        }
    }
}
