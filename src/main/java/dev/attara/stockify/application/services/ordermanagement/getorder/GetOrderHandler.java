package dev.attara.stockify.application.services.ordermanagement.getorder;

import dev.attara.stockify.application.dtos.OrderRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.models.Order;
import dev.attara.stockify.domain.repositories.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.mappers.OrderMapper;
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
