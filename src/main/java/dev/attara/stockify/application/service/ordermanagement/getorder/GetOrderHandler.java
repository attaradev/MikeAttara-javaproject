package dev.attara.stockify.application.service.ordermanagement.getorder;

import dev.attara.stockify.application.dto.OrderRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.repository.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Handler for retrieving a specific order.
 */
@Service
@RequiredArgsConstructor
public class GetOrderHandler implements ServiceHandler<GetOrder, OrderRecord> {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    /**
     * Retrieves the order specified by the given request.
     *
     * @param getOrder The request to retrieve a specific order.
     * @return The details of the retrieved order.
     */
    @Override
    public OrderRecord handle(GetOrder getOrder) {
        Order order = repository.findById(getOrder.id());
        return mapper.mapToRecord(order);
    }
}
