package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.output.ProductRecord;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.infrastructure.persistence.entity.ProductEntity;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product mapToDomain(@NonNull ProductEntity entity) {
        return Product.create(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock()
        );
    }

    public ProductRecord mapToRecord(@NonNull Product product) {
        return new ProductRecord(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getPrice()
        );
    }

    public ProductEntity mapToEntity(@NonNull Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setStock(product.getStock());
        entity.setPrice(product.getPrice());
        return entity;
    }

}
