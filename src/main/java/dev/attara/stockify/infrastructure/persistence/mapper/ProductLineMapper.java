package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.domain.model.Product;
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

    public ProductLineRecord toRecord(@NonNull ProductLine model) {
        Product product = productRepository.findById(model.getProductId());
        return new ProductLineRecord(
                productMapper.toRecord(product),
                model.getQuantity()

        );
    }

    public ProductLineEntity toEntity(@NonNull ProductLine model) {
        ProductLineEntity entity = new ProductLineEntity();
        entity.setProductId(model.getProductId());
        entity.setQuantity(model.getQuantity());
        entity.setOrderId(model.getOrderId());
        return entity;
    }
}
