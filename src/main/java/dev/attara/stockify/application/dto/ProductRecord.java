package dev.attara.stockify.application.dto;

/**
 * A record representing a product, including its ID, name, stock quantity, and price.
 */
public record ProductRecord(
        String id,

        String name,

        int stock,

        double price
) { }
