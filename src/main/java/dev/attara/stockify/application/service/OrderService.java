package dev.attara.stockify.application.service;

import dev.attara.stockify.application.dto.input.CreateOrderDTO;
import dev.attara.stockify.application.dto.input.ProductLineDTO;
import dev.attara.stockify.application.dto.input.UpdateProductDTO;
import dev.attara.stockify.application.dto.output.OrderRecord;
import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.domain.exception.NotAuthorizedException;
import dev.attara.stockify.domain.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderRecord createOrder(CreateOrderDTO createOrderDTO);
    OrderRecord updateOrder(Long orderId, List<ProductLineDTO> productLines);
    List<OrderRecord> allOrders();
    List<OrderRecord> ordersByUserId(Long userId);
    OrderRecord getOrderById(Long orderId) throws OrderNotFoundException;
    List<ProductLineRecord> getOrderProducts(Long orderId);
    boolean deleteOrder(Long orderId) throws OrderNotFoundException;
}
