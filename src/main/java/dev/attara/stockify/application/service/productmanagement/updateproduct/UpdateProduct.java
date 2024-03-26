package dev.attara.stockify.application.service.productmanagement.updateproduct;

/**
 * Represents a request to update a product.
 */
public record UpdateProduct(
        long id,

        ProductUpdateData updateData
) {
}
