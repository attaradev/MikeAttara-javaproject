package dev.attara.stockify.application.service.ordermanagement.updateorder;

import dev.attara.stockify.application.service.ServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validator for the UpdateOrder service.
 * It ensures that the update request contains product lines.
 */
@Component
@RequiredArgsConstructor
public class UpdateOrderValidator implements ServiceValidator<UpdateOrder> {

    /**
     * Validates the update order request.
     *
     * @param updateOrder The update order request to be validated.
     * @throws IllegalArgumentException if the product lines are empty.
     */
    @Override
    public void validate(UpdateOrder updateOrder) throws IllegalArgumentException {
        if (updateOrder.orderId()==null) {
            throw new IllegalArgumentException("orderId required");
        }
        if (updateOrder.productLines().isEmpty()) {
            throw new IllegalArgumentException("Product lines are required to update the order");
        }
    }
}
