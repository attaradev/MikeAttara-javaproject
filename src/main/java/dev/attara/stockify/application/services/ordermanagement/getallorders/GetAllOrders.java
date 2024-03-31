package dev.attara.stockify.application.services.ordermanagement.getallorders;

import dev.attara.stockify.domain.models.User;
import lombok.NonNull;

/**
 * Marker class representing a request to retrieve all orders.
 * This class is used to signal the intention of fetching all orders.
 */
public record GetAllOrders(@NonNull User user) { }
