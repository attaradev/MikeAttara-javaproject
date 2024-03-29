package dev.attara.stockify.application.services.productmanagement.updateproduct;

import dev.attara.stockify.application.services.ServiceValidator;
import org.springframework.stereotype.Component;

/**
 * Validator for validating the update request for a product.
 */
@Component
public class UpdateProductValidator implements ServiceValidator<UpdateProduct> {

    /**
     * Validates the update request for a product.
     *
     * @param updateProduct The update product request to validate.
     * @throws IllegalArgumentException if the update data is null, or if the stock or price is negative.
     */
    @Override
    public void validate(UpdateProduct updateProduct) throws IllegalArgumentException {
        if (updateProduct.updateData() == null) {
            throw new IllegalArgumentException("updateData required");
        }
        int stock = updateProduct.updateData().getStock();
        double price = updateProduct.updateData().getPrice();
        if (stock != Integer.MIN_VALUE && stock < 0) {
            throw new IllegalArgumentException("stock cannot be negative");
        }
        if (price != Float.NEGATIVE_INFINITY && price < 0.0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
    }

}
