package dev.attara.stockify.application.service.productmanagement.deleteproduct;

import lombok.NonNull;

/**
 * A record representing a request to delete a product by its ID.
 */
public record DeleteProduct(@NonNull String id) {
}
