package dev.attara.stockify.application.services.productmanagement.updateproduct;

import lombok.Data;

/**
 * Represents the data used to update a product.
 */
@Data
public class ProductUpdateData {
    /**
     * The new name of the product.
     */
    private String name;

    /**
     * The new stock quantity of the product.
     */
    private int stock = Integer.MIN_VALUE;

    /**
     * The new price of the product.
     */
    private double price = Float.NEGATIVE_INFINITY;
}
