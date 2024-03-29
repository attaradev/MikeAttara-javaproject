package dev.attara.stockify.application.services.ordermanagement.getordersbyuser;

import dev.attara.stockify.application.dtos.OrderRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.repositories.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handler for retrieving orders by user ID.
 */
@Service
@RequiredArgsConstructor
public class GetOrdersByUserHandler implements ServiceHandler<GetOrdersByUser, List<OrderRecord>> {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    /**
     * Handles the request to retrieve orders by user ID.
     *
     * @param getOrdersByUser The request containing the user ID.
     * @return A list of order records associated with the user.
     */
    @Override
    public List<OrderRecord> handle(GetOrdersByUser getOrdersByUser) {
        return repository.findByUserId(getOrdersByUser.userId()).stream()
                .map(mapper::mapToRecord)
                .toList();
    }
}
