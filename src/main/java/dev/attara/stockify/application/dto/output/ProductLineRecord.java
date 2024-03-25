package dev.attara.stockify.application.dto.output;

public record ProductLineRecord(
        Long orderId,
        Long productId,
        int quantity
) {
}
