package dev.attara.stockify.domain.model;

import dev.attara.stockify.application.dto.input.ProductLineDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Order {
    @Getter
    private final long id;
    @Getter
    private final User user;
    private final Map<Long, Integer> products;

    private Order(long id, User user) {
        this.id = id;
        this.user = user;
        this.products = new HashMap<>();
    }

    public static Order create(long id, User user) throws IllegalArgumentException {
        if (id < 0) throw new IllegalArgumentException("Invalid order id");
        if (user == null) throw new IllegalArgumentException("Invalid user");
        return new Order(id, user);
    }

    public List<ProductLine> getProductLines() {
        List<ProductLine> productLines = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            productLines.add(new ProductLine(id, productId, quantity));
        }
        return productLines;
    }

    public void addProducts(List<ProductLineDTO> productLines) {
        productLines.forEach(this::addProduct);
    }

    private void addProduct(ProductLineDTO productLine) {
        long productId = productLine.productId();
        int quantity = productLine.quantity();
        if (products.containsKey(productId)) {
            quantity += products.get(productId);
        }
        products.put(productId, quantity);
    }
}
