package dev.attara.stockify.domain.model;

import lombok.Getter;

import java.util.List;


@Getter
public class Order {

    private final long id;

    private final User user;

    private final List<ProductLine> productLines;

    private Order(long id, User user, List<ProductLine> productLines) {
        this.id = id;
        this.user = user;
        this.productLines = productLines;
    }

    public static Order create(long id, User user, List<ProductLine> productLines) throws IllegalArgumentException {
        if (id < 0) throw new IllegalArgumentException("Invalid order id");
        if (user == null) throw new IllegalArgumentException("Invalid user");
        if (productLines == null || productLines.isEmpty()) throw new IllegalArgumentException("Cannot create order without productLines");
        return new Order(id, user, productLines);
    }


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
