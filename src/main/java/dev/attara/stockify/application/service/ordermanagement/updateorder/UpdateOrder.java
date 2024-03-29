package dev.attara.stockify.application.service.ordermanagement.updateorder;

import dev.attara.stockify.application.service.ordermanagement.createorder.ProductLineData;
import dev.attara.stockify.domain.model.User;

import java.util.List;

/**
 * A record representing a request to update an order with new product lines.
 */
public record UpdateOrder(String orderId, List<ProductLineData> productLines, User user) {
}
