package dev.attara.stockify.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderEntity {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductLineEntity> productLines;

}