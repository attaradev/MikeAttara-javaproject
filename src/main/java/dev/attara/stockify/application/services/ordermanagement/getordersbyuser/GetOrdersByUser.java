package dev.attara.stockify.application.services.ordermanagement.getordersbyuser;

import dev.attara.stockify.domain.models.User;
import lombok.NonNull;

/**
 * A record representing a request to retrieve orders by user ID.
 */
public record GetOrdersByUser(String userId, @NonNull User user) {
}
