package dev.attara.stockify.application.services.productmanagement.addproduct;

import dev.attara.stockify.application.services.ServiceValidator;
import org.springframework.stereotype.Component;

/**
 * Validator for validating the input data for adding a new product.
 */
@Component
public class AddProductValidator implements ServiceValidator<AddProduct> {

    /**
     * Validates the input data for adding a new product.
     *
     * @param addProduct The data representing the new product to be added.
     * @throws IllegalArgumentException If the input data is invalid.
     */
    @Override
    public void validate(AddProduct addProduct) throws IllegalArgumentException {
        if (addProduct.name() == null) throw new IllegalArgumentException("name required");
        if (addProduct.price() < 0) throw new IllegalArgumentException("price cannot be negative");
        if (addProduct.stock() < 0) throw new IllegalArgumentException("stock cannot be negative");
    }

}
