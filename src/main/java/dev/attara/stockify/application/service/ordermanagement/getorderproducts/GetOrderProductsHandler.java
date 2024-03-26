package dev.attara.stockify.application.service.ordermanagement.getorderproducts;

import dev.attara.stockify.application.dto.OrderRecord;
import dev.attara.stockify.application.dto.ProductLineRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.repository.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.OrderMapper;
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
