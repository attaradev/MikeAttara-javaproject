package dev.attara.stockify.application.service.productmanagement.getlowstockproducts;

/**
 * A record representing a request to retrieve products with stock levels below a certain threshold.
 */
public record GetLowStockProducts(int threshold) {
}
