package dev.attara.stockify.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(nullable = false)
    private OrderEntity order;

}
