package dev.attara.stockify.application.services.ordermanagement.updateorder;

import dev.attara.stockify.application.dtos.OrderRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.models.Order;
import dev.attara.stockify.domain.models.ProductLine;
import dev.attara.stockify.domain.services.OrderManager;
import dev.attara.stockify.infrastructure.persistence.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handler for updating an existing order with new product lines.
 */
@Service
@RequiredArgsConstructor
public class UpdateOrderHandler implements ServiceHandler<UpdateOrder, OrderRecord> {

    private final OrderManager orderManager;

    private final UpdateOrderValidator validator;

    private final OrderMapper mapper;

    /**
     * Handles the request to update an existing order with new product lines.
     *
     * @param updateOrder The request containing the order ID and the new product lines.
     * @return The updated order record after the update operation.
     * @throws IllegalArgumentException if the product lines are empty.
     */
    @Override
    public OrderRecord handle(UpdateOrder updateOrder) throws IllegalArgumentException {
        validator.validate(updateOrder);
        String orderId = updateOrder.orderId();
        List<ProductLine> productLines = updateOrder.productLines().stream()
                .map(orderManager::createProductLine)
                .toList();
        Order order = orderManager.updateOrder(orderId, productLines);
        return mapper.mapToRecord(order);
    }
}
