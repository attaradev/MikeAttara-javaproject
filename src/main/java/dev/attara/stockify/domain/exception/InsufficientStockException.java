package dev.attara.stockify.domain.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(long productId) {
        super("Insufficient stock available for Product ID: " + productId);
    }

}
