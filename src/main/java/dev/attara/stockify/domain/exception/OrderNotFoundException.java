package dev.attara.stockify.domain.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(long orderId) {
        super("Order not found with ID: " + orderId);
    }

}
