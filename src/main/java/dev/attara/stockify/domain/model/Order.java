package dev.attara.stockify.domain.model;

import dev.attara.stockify.application.dto.input.ProductLineDTO;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class Order {
    @Getter
    private final Long id;
    @Getter
    private final User user;
    private final Map<Long, Integer> products = new HashMap<>();

    public List<ProductLine> getProductLines() {
        List<ProductLine> productLines = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            productLines.add(new ProductLine(productId, quantity, id));
        }
        return productLines;
    }

    public void addProducts(@NonNull List<ProductLineDTO> productLines) {
        productLines.forEach(this::addProduct);
    }

    private void addProduct(@NonNull ProductLineDTO productLine) {
        long productId = productLine.productId();
        int quantity = productLine.quantity();

        products.merge(productId, quantity, Integer::sum);
    }
}
