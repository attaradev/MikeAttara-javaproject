package dev.attara.stockify.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents an order entity in the database.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderEntity {

    /** The unique identifier of the order. */
    @Id
    private String id;

    /** The user associated with this order. */
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    /** The list of product lines associated with this order. */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductLineEntity> productLines;

}
