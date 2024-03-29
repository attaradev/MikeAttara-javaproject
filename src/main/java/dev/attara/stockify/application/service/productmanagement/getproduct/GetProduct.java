package dev.attara.stockify.application.service.productmanagement.getproduct;

import lombok.NonNull;

/**
 * A record representing a request to retrieve a product by its ID.
 */
public record GetProduct(@NonNull String id) {
}
