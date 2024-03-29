package dev.attara.stockify.application.services.ordermanagement.getorderproducts;

import dev.attara.stockify.domain.models.User;

/**
 * Represents a request to retrieve the products associated with a specific order.
 */
public record GetOrderProducts(String orderId, User user) {
}
