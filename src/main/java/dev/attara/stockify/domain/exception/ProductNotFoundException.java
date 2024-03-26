package dev.attara.stockify.domain.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long productId) {
        super("Product not found with ID: " + productId);
    }
}
