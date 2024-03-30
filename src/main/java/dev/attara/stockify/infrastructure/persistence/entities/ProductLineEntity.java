package dev.attara.stockify.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an entity for the product line.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(ProductLineId.class)
public class ProductLineEntity {

    /**
     * The order ID.
     */
    @Id
    @Column(name = "order_id", nullable = false)
    private String orderId;

    /**
     * The product ID.
     */
    @Id
    @Column(name = "product_id", nullable = false)
    private String productId;

    /**
     * The order associated with this product line.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrderEntity order;

    /**
     * The product associated with this product line.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductEntity product;

    /**
     * The quantity of the product in the order.
     */
    @Column(nullable = false)
    private int quantity;

}
