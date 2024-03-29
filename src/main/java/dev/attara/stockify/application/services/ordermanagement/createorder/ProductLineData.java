package dev.attara.stockify.application.services.ordermanagement.createorder;

/**
 * Represents the data for a product line in an order, including the product ID and quantity.
 */
public record ProductLineData(String productId, int quantity) {
}
