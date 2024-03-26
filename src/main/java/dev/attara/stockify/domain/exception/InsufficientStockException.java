package dev.attara.stockify.domain.exception;

/**
 * Exception thrown when there is insufficient stock available for a product.
 */
public class InsufficientStockException extends RuntimeException {

    /**
     * Constructs an InsufficientStockException with the specified product ID.
     *
     * @param productId the ID of the product for which the stock is insufficient
     */
    public InsufficientStockException(long productId) {
        super("Insufficient stock available for Product ID: " + productId);
    }

}
