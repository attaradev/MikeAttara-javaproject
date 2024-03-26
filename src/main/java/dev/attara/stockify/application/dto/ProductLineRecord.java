package dev.attara.stockify.application.dto;

/**
 * A record representing a product line, including the product details and quantity.
 */
public record ProductLineRecord(
        ProductRecord product,

        int quantity
) { }
