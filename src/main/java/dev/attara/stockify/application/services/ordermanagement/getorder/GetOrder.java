package dev.attara.stockify.application.services.ordermanagement.getorder;

import dev.attara.stockify.domain.models.User;

/**
 * Represents a request to retrieve a specific order.
 */
public record GetOrder(String id, User user) {
}
