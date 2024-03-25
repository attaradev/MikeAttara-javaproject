package dev.attara.stockify.application.dto.input;

public record UpdateProductDTO(
        String name,
        int stock,
        double price
) {
}
