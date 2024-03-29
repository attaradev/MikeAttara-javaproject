package dev.attara.stockify.application.services.ordermanagement.updateorder;

import dev.attara.stockify.application.services.ordermanagement.createorder.ProductLineData;
import dev.attara.stockify.domain.models.User;

import java.util.List;

/**
 * A record representing a request to update an order with new product lines.
 */
public record UpdateOrder(String orderId, List<ProductLineData> productLines, User user) {
}
