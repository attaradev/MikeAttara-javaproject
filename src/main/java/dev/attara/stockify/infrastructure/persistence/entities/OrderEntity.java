package dev.attara.stockify.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an order entity in the database.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderEntity {

    /**
     * The unique identifier of the order.
     */
    @Id
    private String id;

    /**
     * The user who placed the order.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    /**
     * The product lines associated with this order.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyJoinColumn(name = "product_id")
    private Map<String, ProductLineEntity> productLines = new HashMap<>();

    public void addProductLines(List<ProductLineEntity> productLineEntities) {
        for (ProductLineEntity productLine: productLineEntities) {
            productLine.setOrderId(this.id);
            productLine.setOrder(this);
            productLines.put(productLine.getProduct().getId(), productLine);
        }
    }
}
