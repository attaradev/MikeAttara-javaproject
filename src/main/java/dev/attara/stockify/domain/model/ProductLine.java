package dev.attara.stockify.domain.model;

import lombok.Data;

@Data
public class ProductLine {

    private Product product;
    private int quantity;

    private ProductLine(Product product, int quantity) {
        this.setProduct(product);
        this.setQuantity(quantity);
    }

    /**
     * Creates a new product line.
     *
     * @param product  the product in the line
     * @param quantity the quantity of the product
     * @return the created product line
     * @throws IllegalArgumentException if the product is null or the quantity is negative
     */
    public static ProductLine create(Product product, int quantity) throws IllegalArgumentException {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
        return new ProductLine(product, quantity);
    }
}
