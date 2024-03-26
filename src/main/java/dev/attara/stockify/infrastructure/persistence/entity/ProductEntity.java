package dev.attara.stockify.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductEntity {

    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private double price;

}