package dev.attara.stockify.application.services.ordermanagement.getorderproducts;

import dev.attara.stockify.application.dtos.OrderRecord;
import dev.attara.stockify.application.dtos.ProductLineRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.models.Order;
import dev.attara.stockify.domain.repositories.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handler for retrieving the products associated with a specific order.
 */
@Service
@RequiredArgsConstructor
public class GetOrderProductsHandler implements ServiceHandler<GetOrderProducts, List<ProductLineRecord>> {

    private final OrderMapper mapper;
    private final OrderRepository repository;

    /**
     * Handles the request to retrieve the products associated with a specific order.
     *
     * @param getOrderProducts The request to retrieve the products associated with a specific order.
     * @return A list of product line records associated with the order.
     */
    @Override
    public List<ProductLineRecord> handle(GetOrderProducts getOrderProducts) {
        Order order = repository.findById(getOrderProducts.orderId());
        OrderRecord orderRecord = mapper.mapToRecord(order);
        return orderRecord.productLines();
    }
}
