package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.output.OrderRecord;
import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.model.ProductLine;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.infrastructure.persistence.entity.OrderEntity;
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

    public Order mapToDomain(@NonNull OrderEntity entity) {
        List<ProductLine> productLines = entity.getProductLines().stream().map(productLineMapper::mapToDomain).toList();
        User user = userMapper.mapToDomain(entity.getUser());
        return Order.create(entity.getId(), user,productLines);
    }

    public OrderRecord mapToRecord(@NonNull Order model) {
        List<ProductLineRecord> productLineRecords = model.getProductLines().stream()
                .map(productLineMapper::mapToRecord)
                .collect(Collectors.toList());
        return new OrderRecord(
                model.getId(),
                userMapper.mapToRecord(model.getUser()),
                productLineRecords
        );
    }

    public OrderEntity mapToEntity(@NonNull Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setProductLines(order.getProductLines().stream()
                .map(productLineMapper::mapToEntity)
                .toList());
        entity.setUser(userMapper.mapToEntity(order.getUser()));
        return entity;
    }
}
