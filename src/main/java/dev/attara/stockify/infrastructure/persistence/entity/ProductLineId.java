package dev.attara.stockify.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@AllArgsConstructor
public class ProductLineId implements Serializable {
    private final long orderId;
    private final long productId;
}
