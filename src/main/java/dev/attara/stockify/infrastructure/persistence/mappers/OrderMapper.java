package dev.attara.stockify.infrastructure.persistence.mappers;

import dev.attara.stockify.application.dtos.OrderRecord;
import dev.attara.stockify.application.dtos.ProductLineRecord;
import dev.attara.stockify.application.mappers.Mapper;
import dev.attara.stockify.domain.models.Order;
import dev.attara.stockify.domain.models.ProductLine;
import dev.attara.stockify.domain.models.User;
import dev.attara.stockify.infrastructure.persistence.entities.OrderEntity;
import dev.attara.stockify.infrastructure.persistence.entities.ProductLineEntity;
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

    private final ProductMapper productMapper;

    private final UserMapper userMapper;

    /**
     * Maps an OrderEntity object from the database to its corresponding domain model representation.
     *
     * @param entity The OrderEntity object to map.
     * @return The mapped Order domain model object.
     */
    public Order mapToDomain(@NonNull OrderEntity entity) {
        User user = userMapper.mapToDomain(entity.getUser());
        List<ProductLine> productLines = entity.getProductLines().values().stream()
                .map(productLineEntity -> ProductLine.create(productMapper.mapToDomain(
                                productLineEntity.getProduct()),
                        productLineEntity.getQuantity()
                ))
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
                .map(productLine -> new ProductLineRecord(productMapper.mapToRecord(productLine.getProduct()), productLine.getQuantity()))
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
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setUser(userMapper.mapToEntity(order.getUser()));
        orderEntity.addProductLines(order.getProductLines().stream().map(productLine -> {
            ProductLineEntity productLineEntity = new ProductLineEntity();
            productLineEntity.setQuantity(productLine.getQuantity());
            productLineEntity.setProductId(productLine.getProduct().getId());
            productLineEntity.setProduct(productMapper.mapToEntity(productLine.getProduct()));
            return productLineEntity;
        }).toList());
        return orderEntity;
    }

}
