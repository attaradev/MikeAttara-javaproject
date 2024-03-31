package dev.attara.stockify.application.services.ordermanagement.getallorders;

import dev.attara.stockify.application.dtos.OrderRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.models.Order;
import dev.attara.stockify.domain.models.User;
import dev.attara.stockify.domain.repositories.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.mappers.OrderMapper;
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
        User user = getAllOrders.user();
        List<Order> orderList;

        if (user.isNotAdmin()) {
            orderList =  repository.findByUserId(user.getId());
        } else {
            orderList = repository.findAll();
        }

        return orderList.stream()
                .map(mapper::mapToRecord)
                .toList();
    }
}
