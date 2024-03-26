package dev.attara.stockify.application.service.impl;

import dev.attara.stockify.application.dto.input.CreateOrderDTO;
import dev.attara.stockify.application.dto.input.ProductLineDTO;
import dev.attara.stockify.application.dto.output.OrderRecord;
import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.application.service.OrderService;
import dev.attara.stockify.domain.exception.OrderNotFoundException;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.OrderRepository;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.OrderMapper;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductLineMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final ProductLineMapper productLineMapper;

    @Override
    public OrderRecord createOrder(CreateOrderDTO createOrderDTO) {
        List<ProductLineDTO> products = createOrderDTO.productLines();
        products.forEach(this::processProductLine);
        Order order = Order.create(orderRepository.nextId(), createOrderDTO.user());
        order.addProducts(createOrderDTO.productLines());
        orderRepository.save(order);
        return orderMapper.toRecord(order);
    }

    @Override
    public OrderRecord updateOrder(long orderId, List<ProductLineDTO> productLines, User user) {
        Order order = orderRepository.findById(orderId);
        order.addProducts(productLines);
        orderRepository.save(order);
        return orderMapper.toRecord(order);
    }

    @Override
    public List<OrderRecord> allOrders(User user) {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public List<OrderRecord> ordersByUserId(long userId, User user) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderMapper::toRecord).toList();
    }

    @Override
    public OrderRecord getOrderById(long orderId, User user) {
        try {
            return orderMapper.toRecord(orderRepository.findById(orderId));
        } catch (OrderNotFoundException e) {
            System.err.println("Order not found for ID: " + orderId);
            return null;
        }
    }

    @Override
    public List<ProductLineRecord> getOrderProducts(long orderId, User user) {
        try {
            return orderRepository.findById(orderId).getProductLines()
                    .stream()
                    .map(productLineMapper::toRecord)
                    .collect(Collectors.toList());
        } catch (OrderNotFoundException e) {
            // Handle case where order is not found
            // Log the exception for debugging purposes
            System.err.println("Order not found for ID: " + orderId);
            return null;
        }
    }

    @Override
    public boolean deleteOrder(long orderId, User user) {
        Order order = orderRepository.findById(orderId);
        orderRepository.delete(order);
        return true;
    }

    private void processProductLine(@NonNull ProductLineDTO productLine) {
        Product product = productRepository.findById(productLine.productId());
        product.sell(productLine.quantity());
        productRepository.save(product);
    }
}
