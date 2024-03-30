package dev.attara.stockify.infrastructure.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    /**
     * The unique identifier of the product.
     */
    @Id
    private String id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The price of the product.
     */
    private double price;

    /**
     * The stock of the product.
     */
    private int stock;

}
