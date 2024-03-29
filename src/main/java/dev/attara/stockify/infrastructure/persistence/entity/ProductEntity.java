package dev.attara.stockify.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a product entity in the database.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductEntity {

    /** The unique identifier of the product. */
    @Id
    private String id;

    /** The name of the product. */
    @Column(nullable = false)
    private String name;

    /** The current stock quantity of the product. */
    @Column(nullable = false)
    private int stock;

    /** The price of the product. */
    @Column(nullable = false)
    private double price;

}
