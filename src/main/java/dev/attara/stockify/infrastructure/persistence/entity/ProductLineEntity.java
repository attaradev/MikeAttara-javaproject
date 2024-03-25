package dev.attara.stockify.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_lines")
@IdClass(ProductLineId.class)
public class ProductLineEntity {
    @Id
    @Column(nullable = false, name = "product_id")
    private Long productId;

    @Id
    @Column(nullable = false, name = "order_id")
    private Long orderId;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
}
