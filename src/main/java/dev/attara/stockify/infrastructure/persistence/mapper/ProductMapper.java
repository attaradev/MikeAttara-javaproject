package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.output.ProductRecord;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.infrastructure.persistence.entity.ProductEntity;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toModel(@NonNull ProductEntity entity) {
        return Product.create(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock()
        );
    }

    public ProductRecord toRecord(@NonNull Product model) {
        return new ProductRecord(
                model.getId(),
                model.getName(),
                model.getStock(),
                model.getPrice()
        );
    }

    public ProductEntity toEntity(@NonNull Product model) {
        ProductEntity entity = new ProductEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setStock(model.getStock());
        entity.setPrice(model.getPrice());
        return entity;
    }

}
