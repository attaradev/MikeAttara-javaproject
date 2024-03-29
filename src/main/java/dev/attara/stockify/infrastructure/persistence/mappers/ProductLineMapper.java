package dev.attara.stockify.infrastructure.persistence.mappers;

import dev.attara.stockify.application.dtos.ProductLineRecord;
import dev.attara.stockify.application.mappers.Mapper;
import dev.attara.stockify.domain.models.ProductLine;
import dev.attara.stockify.infrastructure.persistence.entities.ProductLineEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping ProductLine objects between their domain representation, database entity, and DTO representation.
 */
@Component
@RequiredArgsConstructor
public class ProductLineMapper implements Mapper<ProductLine, ProductLineRecord, ProductLineEntity> {

    private final ProductMapper productMapper;

    /**
     * Maps a ProductLine domain model object to its corresponding DTO representation.
     *
     * @param productLine The ProductLine domain model object to map.
     * @return The mapped ProductLineRecord DTO object.
     */
    public ProductLineRecord mapToRecord(ProductLine productLine) {
        return new ProductLineRecord(
                productMapper.mapToRecord(productLine.getProduct()),
                productLine.getQuantity()
        );
    }

    /**
     * Maps a ProductLineEntity object from the database to its corresponding domain model representation.
     *
     * @param entity The ProductLineEntity object to map.
     * @return The mapped ProductLine domain model object.
     */
    public ProductLine mapToDomain(ProductLineEntity entity) {
        return ProductLine.create(productMapper.mapToDomain(entity.getProduct()), entity.getQuantity());
    }

    /**
     * Maps a ProductLine domain model object to its corresponding database entity representation.
     *
     * @param productLine The ProductLine domain model object to map.
     * @return The mapped ProductLineEntity database entity object.
     */
    public ProductLineEntity mapToEntity(ProductLine productLine) {
        ProductLineEntity productLineEntity = new ProductLineEntity();
        productLineEntity.setProduct(productMapper.mapToEntity(productLine.getProduct()));
        productLineEntity.setQuantity(productLine.getQuantity());
        return productLineEntity;
    }
}
