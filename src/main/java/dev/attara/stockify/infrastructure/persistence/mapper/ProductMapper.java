package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.ProductRecord;
import dev.attara.stockify.domain.mapper.Mapper;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping Product objects between their domain representation, database entity, and DTO representation.
 */
@Component
public class ProductMapper implements Mapper<Product, ProductRecord, ProductEntity> {

    /**
     * Maps a ProductEntity object from the database to its corresponding domain model representation.
     *
     * @param entity The ProductEntity object to map.
     * @return The mapped Product domain model object.
     */
    public Product mapToDomain(ProductEntity entity) {
        return Product.create(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock()
        );
    }

    /**
     * Maps a Product domain model object to its corresponding DTO representation.
     *
     * @param product The Product domain model object to map.
     * @return The mapped ProductRecord DTO object.
     */
    public ProductRecord mapToRecord(Product product) {
        return new ProductRecord(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getPrice()
        );
    }

    /**
     * Maps a Product domain model object to its corresponding database entity representation.
     *
     * @param product The Product domain model object to map.
     * @return The mapped ProductEntity database entity object.
     */
    public ProductEntity mapToEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setStock(product.getStock());
        entity.setPrice(product.getPrice());
        return entity;
    }

}
