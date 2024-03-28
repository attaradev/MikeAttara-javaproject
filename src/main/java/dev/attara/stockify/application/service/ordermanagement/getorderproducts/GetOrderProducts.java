package dev.attara.stockify.application.service.ordermanagement.getorderproducts;

import dev.attara.stockify.domain.model.User;

/**
 * Represents a request to retrieve the products associated with a specific order.
 */
public record GetOrderProducts(long orderId, User user) {
}
