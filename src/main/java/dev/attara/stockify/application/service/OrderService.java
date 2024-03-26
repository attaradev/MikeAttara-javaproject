package dev.attara.stockify.application.service;

import dev.attara.stockify.application.dto.input.CreateOrderDTO;
import dev.attara.stockify.application.dto.input.ProductLineDTO;
import dev.attara.stockify.application.dto.output.OrderRecord;
import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.domain.exception.OrderNotFoundException;
import dev.attara.stockify.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderRecord createOrder(CreateOrderDTO createOrderDTO);
    OrderRecord updateOrder(long orderId, List<ProductLineDTO> productLines, User user);
    List<OrderRecord> allOrders(User user);
    List<OrderRecord> ordersByUserId(long userId, User user);
    OrderRecord getOrderById(long orderId, User user) throws OrderNotFoundException;
    List<ProductLineRecord> getOrderProducts(long orderId, User user);
    boolean deleteOrder(long orderId, User user) throws OrderNotFoundException;
}
