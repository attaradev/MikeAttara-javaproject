package dev.attara.stockify.application.service.ordermanagement.deleteorder;

import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Handles the deletion of an order based on the provided {@link DeleteOrder} request.
 */
@Service
@RequiredArgsConstructor
public class DeleteOrderHandler implements ServiceHandler<DeleteOrder, Boolean> {

    private final OrderRepository repository;

    /**
     * Deletes the order specified in the {@code deleteOrder} request.
     *
     * @param deleteOrder The request containing the ID of the order to delete and the associated user.
     * @return {@code true} if the order is successfully deleted, {@code false} otherwise.
     */
    @Override
    public Boolean handle(DeleteOrder deleteOrder) {
        Order order = repository.findById(deleteOrder.id());
        repository.delete(order);
        return true;
    }
}
