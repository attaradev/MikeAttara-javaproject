package dev.attara.stockify.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a product line entity in the database.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductLineEntity {

    /** The unique identifier of the product line. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** The quantity of the product in the order. */
    @Column(nullable = false)
    private int quantity;

    /** The product associated with this product line. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ProductEntity product;

    /** The order associated with this product line. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private OrderEntity order;

}
