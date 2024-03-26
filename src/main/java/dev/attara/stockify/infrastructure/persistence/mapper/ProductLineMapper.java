package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.input.ProductLineDTO;
import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.domain.model.ProductLine;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.entity.ProductLineEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductLineMapper {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductLineRecord mapToRecord(@NonNull ProductLine productLine) {
        return new ProductLineRecord(
                productMapper.mapToRecord(productLine.getProduct()),
                productLine.getQuantity()
        );
    }

    public ProductLine mapToDomain(ProductLineDTO dto) {
        return ProductLine.create(productRepository.findById(dto.productId()), dto.quantity());
    }

    public ProductLine mapToDomain(ProductLineEntity entity) {
        return ProductLine.create(productMapper.mapToDomain(entity.getProduct()),entity.getQuantity());
    }

    public ProductLineEntity mapToEntity(ProductLine productLine) {
        ProductLineEntity productLineEntity = new ProductLineEntity();
        productLineEntity.setProduct(productMapper.mapToEntity(productLine.getProduct()));
        productLineEntity.setQuantity(productLine.getQuantity());
        return productLineEntity;
    }

}
