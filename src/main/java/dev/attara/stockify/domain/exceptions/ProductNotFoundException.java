package dev.attara.stockify.domain.exceptions;

/**
 * Custom exception class to represent the scenario where a product is not found.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructor that takes the product ID as a parameter and constructs an error message.
     *
     * @param productId The ID of the product that was not found.
     */
    public ProductNotFoundException(String productId) {
        super("Product not found with ID: " + productId);
    }

}
