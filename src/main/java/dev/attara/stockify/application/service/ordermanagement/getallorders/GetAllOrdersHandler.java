package dev.attara.stockify.application.service.ordermanagement.getallorders;

import dev.attara.stockify.application.dto.OrderRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.repository.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handler responsible for retrieving all orders.
 */
@Service
@RequiredArgsConstructor
public class GetAllOrdersHandler implements ServiceHandler<GetAllOrders, List<OrderRecord>> {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    /**
     * Retrieves all orders from the repository and maps them to order records.
     *
     * @param getAllOrders Marker object indicating the request to retrieve all orders.
     * @return List of order records representing all orders.
     */
    @Override
    public List<OrderRecord> handle(GetAllOrders getAllOrders) {
        return repository.findAll().stream()
                .map(mapper::mapToRecord)
                .toList();
    }
}
