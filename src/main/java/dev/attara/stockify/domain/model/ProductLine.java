package dev.attara.stockify.domain.model;

import lombok.*;

@Getter
@AllArgsConstructor
public class ProductLine {
    private final long orderId;
    private final long productId;
    private int quantity;
}
