package dev.attara.stockify.application.dto.input;

import dev.attara.stockify.domain.annotations.NonNegative;
import org.jetbrains.annotations.NotNull;

public record CreateProductDTO(
        @NotNull String name,
        @NonNegative int stock,
        @NonNegative double price
) {
}
