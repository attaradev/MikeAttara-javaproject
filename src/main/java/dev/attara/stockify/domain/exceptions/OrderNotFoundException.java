package dev.attara.stockify.domain.exceptions;

/**
 * Exception thrown when an order is not found.
 */
public class OrderNotFoundException extends RuntimeException {

    /**
     * Constructs an OrderNotFoundException with the specified order ID.
     *
     * @param orderId the ID of the order that was not found
     */
    public OrderNotFoundException(String orderId) {
        super("Order not found with ID: " + orderId);
    }

}
