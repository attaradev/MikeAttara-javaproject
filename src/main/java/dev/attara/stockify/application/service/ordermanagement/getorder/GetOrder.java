package dev.attara.stockify.application.service.ordermanagement.getorder;

import dev.attara.stockify.domain.model.User;

/**
 * Represents a request to retrieve a specific order.
 */
public record GetOrder(long id, User user) {
}
