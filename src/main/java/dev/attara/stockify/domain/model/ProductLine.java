package dev.attara.stockify.domain.model;

import lombok.*;

@Data
public class ProductLine {

    private Product product;

    private int quantity;

    private ProductLine(Product product, int quantity) {
        this.setProduct(product);
        this.setQuantity(quantity);
    }

    public static ProductLine create(Product product, int quantity) throws IllegalArgumentException {
        if (product == null) throw new IllegalArgumentException("invalid product");
        if (quantity < 0) throw new IllegalArgumentException("invalid quantity");
        return new ProductLine(product, quantity);
    }

}
