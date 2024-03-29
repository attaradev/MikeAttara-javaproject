package dev.attara.stockify.domain.models;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class Order {

    private final String id;
    private final User user;
    private final List<ProductLine> productLines;

    private Order(String id, User user, List<ProductLine> productLines) {
        this.id = id;
        this.user = user;
        this.productLines = productLines;
    }

    /**
     * Creates a new order.
     *
     * @param id           the ID of the order
     * @param user         the user placing the order
     * @param productLines the list of product lines in the order
     * @return the created order
     * @throws IllegalArgumentException if the ID is invalid, user is null, or product lines are empty
     */
    public static Order create(String id, @NonNull User user, @NonNull List<ProductLine> productLines) throws IllegalArgumentException {
        if (id == null) throw new IllegalArgumentException("Invalid order ID");
        if (productLines.isEmpty()) throw new IllegalArgumentException("Product lines cannot be empty");
        return new Order(id, user, productLines);
    }

    /**
     * Adds a product line to the order. If a product line for the same product already exists, increments its quantity.
     *
     * @param productLine the product line to add
     */
    public void addProductLine(ProductLine productLine) {
        for (ProductLine existingProductLine : productLines) {
            if (existingProductLine.getProduct().getId() == productLine.getProduct().getId()) {
                existingProductLine.setQuantity(existingProductLine.getQuantity() + productLine.getQuantity());
                return;
            }
        }
        productLines.add(productLine);
    }
}
