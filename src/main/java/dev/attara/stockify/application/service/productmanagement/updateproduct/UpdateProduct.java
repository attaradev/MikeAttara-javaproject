package dev.attara.stockify.application.service.productmanagement.updateproduct;

import lombok.NonNull;

/**
 * Represents a request to update a product.
 */
public record UpdateProduct(
        @NonNull String id,

        ProductUpdateData updateData
) {
}
