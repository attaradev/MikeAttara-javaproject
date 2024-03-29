package dev.attara.stockify.application.service.ordermanagement.updateorder;

import dev.attara.stockify.application.dto.OrderRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.model.ProductLine;
import dev.attara.stockify.domain.service.OrderService;
import dev.attara.stockify.infrastructure.persistence.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handler for updating an existing order with new product lines.
 */
@Service
@RequiredArgsConstructor
public class UpdateOrderHandler implements ServiceHandler<UpdateOrder, OrderRecord> {

    private final OrderService orderService;

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
                .map(orderService::createProductLine)
                .toList();
        Order order = orderService.updateOrder(orderId, productLines);
        return mapper.mapToRecord(order);
    }
}
