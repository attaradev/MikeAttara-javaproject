package dev.attara.stockify.application.service.ordermanagement.createorder;

import dev.attara.stockify.application.dto.OrderRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.model.ProductLine;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.service.OrderService;
import dev.attara.stockify.infrastructure.persistence.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handler responsible for creating orders based on the provided data.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderHandler implements ServiceHandler<CreateOrder, OrderRecord> {

    private final CreateOrderValidator validator;
    private final OrderMapper mapper;
    private final OrderService orderService;

    /**
     * Handles the creation of an order based on the provided order data.
     *
     * @param createOrder The data representing the order to be created.
     * @return The record of the created order.
     * @throws RuntimeException If an error occurs during the order creation process.
     */
    @Override
    public OrderRecord handle(CreateOrder createOrder) {
        try {
            // Validate the input data
            validator.validate(createOrder);

            // Extract user and product line data
            User user = createOrder.user();
            List<ProductLine> productLines = createOrder.productLines().stream()
                    .map(orderService::createProductLine)
                    .toList();

            // Create the order using the order service
            Order order = orderService.createOrder(productLines, user);

            // Map the created order to an order record
            return mapper.mapToRecord(order);
        } catch (Exception ex) {
            // Log any errors that occur during the order creation process
            log.error("Failed to create order: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
