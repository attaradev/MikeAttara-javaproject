package dev.attara.stockify.application.service.ordermanagement.createorder;

import dev.attara.stockify.domain.model.User;

import java.util.List;

/**
 * Represents the data needed to create an order.
 */
public record CreateOrder(
        List<ProductLineData> productLines,
        User user
) {
}
