package dev.attara.stockify.application.service.productmanagement.addproduct;

/**
 * Represents a request to add a new product.
 */
public record AddProduct(
        String name,
        double price,
        int stock
) {
}
