package dev.attara.stockify.infrastructure.persistence.mappers;

import dev.attara.stockify.application.dtos.OrderRecord;
import dev.attara.stockify.application.dtos.ProductLineRecord;
import dev.attara.stockify.application.mappers.Mapper;
import dev.attara.stockify.domain.models.Order;
import dev.attara.stockify.domain.models.ProductLine;
import dev.attara.stockify.domain.models.User;
import dev.attara.stockify.infrastructure.persistence.entities.OrderEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class responsible for mapping Order objects between their domain representation, database entity, and DTO representation.
 */
@Component
@RequiredArgsConstructor
public class OrderMapper implements Mapper<Order, OrderRecord, OrderEntity> {

    private final ProductLineMapper productLineMapper;

    private final UserMapper userMapper;

    /**
     * Maps an OrderEntity object from the database to its corresponding domain model representation.
     *
     * @param entity The OrderEntity object to map.
     * @return The mapped Order domain model object.
     */
    public Order mapToDomain(@NonNull OrderEntity entity) {
        User user = userMapper.mapToDomain(entity.getUser());
        List<ProductLine> productLines = entity.getProductLines().stream()
                .map(productLineMapper::mapToDomain)
                .toList();
        return Order.create(entity.getId(), user, productLines);
    }

    /**
     * Maps an Order domain model object to its corresponding DTO representation.
     *
     * @param model The Order domain model object to map.
     * @return The mapped OrderRecord DTO object.
     */
    public OrderRecord mapToRecord(@NonNull Order model) {
        List<ProductLineRecord> productLineRecords = model.getProductLines().stream()
                .map(productLineMapper::mapToRecord)
                .collect(Collectors.toList());
        return new OrderRecord(model.getId(), userMapper.mapToRecord(model.getUser()), productLineRecords);
    }

    /**
     * Maps an Order domain model object to its corresponding database entity representation.
     *
     * @param order The Order domain model object to map.
     * @return The mapped OrderEntity database entity object.
     */
    public OrderEntity mapToEntity(@NonNull Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setUser(userMapper.mapToEntity(order.getUser()));
        List<ProductLine> productLines = order.getProductLines();
        if (productLines != null && !productLines.isEmpty()) {
            entity.setProductLines(productLines.stream()
                    .map(productLineMapper::mapToEntity)
                    .toList());
        }
        return entity;
    }
}
