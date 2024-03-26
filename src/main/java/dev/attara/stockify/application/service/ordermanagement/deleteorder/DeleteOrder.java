package dev.attara.stockify.application.service.ordermanagement.deleteorder;

import dev.attara.stockify.domain.model.User;

/**
 * Represents a request to delete an order, identified by its ID, associated with a user.
 */
public record DeleteOrder(long id, User user) {
}
