package dev.attara.stockify.application.dtos;

/**
 * A record representing a product line, including the product details and quantity.
 */
public record ProductLineRecord(
        ProductRecord product,

        int quantity
) { }
