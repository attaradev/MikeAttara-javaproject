package dev.attara.stockify.application.services.ordermanagement.deleteorder;

import dev.attara.stockify.domain.models.User;

/**
 * Represents a request to delete an order, identified by its ID, associated with a user.
 */
public record DeleteOrder(String id, User user) {
}
