package dev.attara.stockify.application.dto.output;

public record ProductLineRecord(
        ProductRecord product,
        int quantity
) {
}
