package dev.attara.stockify.domain.model;

import dev.attara.stockify.domain.annotations.NonNegative;
import dev.attara.stockify.domain.exception.InsufficientStockException;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {
    private final Long id;
    @NotNull private String name;
    @NonNegative private int stock;
    @NonNegative private double price;

    public void sell(@NonNegative int quantity) throws InsufficientStockException {
        if (stock < quantity) throw new InsufficientStockException(id);
        stock -= quantity;
    }
}
