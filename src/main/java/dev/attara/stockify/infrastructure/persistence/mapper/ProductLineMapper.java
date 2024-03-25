package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.domain.model.ProductLine;
import dev.attara.stockify.infrastructure.persistence.entity.ProductLineEntity;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProductLineMapper {
    public ProductLineRecord toRecord(@NonNull ProductLine model) {
        return new ProductLineRecord(
                model.getOrderId(),
                model.getProductId(),
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
