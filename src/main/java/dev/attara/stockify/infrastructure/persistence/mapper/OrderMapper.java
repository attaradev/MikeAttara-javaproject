package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.input.ProductLineDTO;
import dev.attara.stockify.application.dto.output.OrderRecord;
import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.infrastructure.persistence.entity.OrderEntity;
import dev.attara.stockify.infrastructure.persistence.entity.ProductLineEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ProductLineMapper productLineMapper;

    private final UserMapper userMapper;

    public Order toModel(@NonNull OrderEntity entity) {
        List<ProductLineDTO> productLines = entity.getProductLines().stream()
                .map(this::convert)
                .toList();
        Order order = Order.create(
                entity.getId(),
                userMapper.toModel(entity.getUser())
        );
        order.addProducts(productLines);
        return order;
    }

    public OrderRecord toRecord(@NonNull Order model) {
        List<ProductLineRecord> productLineRecords = model.getProductLines().stream()
                .map(productLineMapper::toRecord)
                .collect(Collectors.toList());
        return new OrderRecord(
                model.getId(),
                productLineRecords,
                userMapper.toRecord(model.getUser())
        );
    }

    public OrderEntity toEntity(@NonNull Order model) {
        OrderEntity entity = new OrderEntity();
        entity.setId(model.getId());
        entity.setProductLines(model.getProductLines().stream()
                .map(productLineMapper::toEntity)
                .collect(Collectors.toList()));
        entity.setUser(userMapper.toEntity(model.getUser()));
        return entity;
    }

    private ProductLineDTO convert(@NonNull ProductLineEntity p) {
        return new ProductLineDTO(p.getProductId(), p.getQuantity());
    }

}
