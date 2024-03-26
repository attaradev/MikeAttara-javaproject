package dev.attara.stockify.application.service.impl;

import dev.attara.stockify.application.dto.input.CreateOrderDTO;
import dev.attara.stockify.application.dto.input.ProductLineDTO;
import dev.attara.stockify.application.dto.output.OrderRecord;
import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.application.service.OrderService;
import dev.attara.stockify.domain.exception.NotAuthorizedException;
import dev.attara.stockify.domain.exception.ProductNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public OrderRecord createOrder(CreateOrderDTO createOrderDTO) {
        List<ProductLineDTO> products = createOrderDTO.productLines();
        try {
            // Process each product line
            products.forEach(this::processProductLine);

            // Create new order
            Order order = Order.create(orderRepository.nextId(), createOrderDTO.user());
            order.addProducts(products);

            // Save order
            orderRepository.save(order);

            return orderMapper.toRecord(order);
        } catch (ProductNotFoundException e) {
            // Handle product not found error
            throw new RuntimeException("Failed to create order: Product not found", e);
        }
    }

    @Override
    @Transactional
    public OrderRecord updateOrder(long orderId, List<ProductLineDTO> productLines, User user) throws NotAuthorizedException {
        // Retrieve existing order
        Order order = orderRepository.findById(orderId);

        // Check if user is authorized to update the order
        if (!order.getUser().equals(user)) {
            throw new NotAuthorizedException();
        }

        try {
            // Process each product line
            productLines.forEach(this::processProductLine);

            // Update order with new product lines
            order.addProducts(productLines);

            // Save updated order
            orderRepository.save(order);

            return orderMapper.toRecord(order);
        } catch (ProductNotFoundException e) {
            // Handle product not found error
            throw new RuntimeException("Failed to update order: Product not found", e);
        }
    }

    @Override
    public List<OrderRecord> allOrders(User user) {
        List<Order> orders = orderRepository.findAll();
        if (user.isNotAdmin()) {
            return orders.stream().filter(order -> order.getUser().equals(user)).map(orderMapper::toRecord).collect(Collectors.toList());
        }
        return orders.stream().map(orderMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public List<OrderRecord> ordersByUserId(long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public OrderRecord getOrderById(long orderId, User user) {
        Order order = orderRepository.findById(orderId);
        if (user.isNotAdmin() && !order.getUser().equals(user)) return null;
        return orderMapper.toRecord(order);
    }

    @Override
    public List<ProductLineRecord> getOrderProducts(long orderId, User user) {
        Order order = orderRepository.findById(orderId);
        if (user.isNotAdmin() && order.getUser().equals(user)) throw new NotAuthorizedException();
        return order.getProductLines()
                .stream()
                .map(productLineMapper::toRecord)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteOrder(long orderId, User user) {
        Order order = orderRepository.findById(orderId);
        orderRepository.delete(order);
        return true;
    }

    private void processProductLine(@NonNull ProductLineDTO productLine) throws ProductNotFoundException {
        Product product = productRepository.findById(productLine.productId());
        if (product == null) {
            throw new ProductNotFoundException(productLine.productId());
        }
        product.sell(productLine.quantity());
        productRepository.save(product);
    }

}
