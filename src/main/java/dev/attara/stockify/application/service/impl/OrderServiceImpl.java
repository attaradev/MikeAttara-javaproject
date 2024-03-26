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
import dev.attara.stockify.domain.model.ProductLine;
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

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final ProductRepository productRepository;

    private final ProductLineMapper productLineMapper;

    @Override
    @Transactional
    public OrderRecord createOrder(CreateOrderDTO dto) {
        try {
            List<ProductLine> productLines = dto.productLines()
                    .stream()
                    .map(productLineMapper::mapToDomain)
                    .toList();
            Order order = Order.create(orderRepository.nextId(), dto.user(),productLines);
            productLines.forEach(this::processProductLine);
            orderRepository.save(order);
            return orderMapper.mapToRecord(order);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException("Failed to create order: Product not found", e);
        }
    }

    @Override
    @Transactional
    public OrderRecord updateOrder(long orderId, List<ProductLineDTO> productLines, User user) throws NotAuthorizedException {
        try {
            Order order = orderRepository.findById(orderId);
            if (user.isNotAdmin() && !order.getUser().equals(user)) throw new NotAuthorizedException();
            List<ProductLine> productLineList = productLines
                    .stream()
                    .map(productLineMapper::mapToDomain)
                    .toList();
            productLineList.forEach(this::processProductLine);
            productLineList.forEach(order::addProductLine);
            orderRepository.save(order);
            return orderMapper.mapToRecord(order);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException("Failed to update order", e);
        }
    }

    @Override
    public List<OrderRecord> allOrders(User user) {
        List<Order> orders = orderRepository.findAll();
        if (user.isNotAdmin()) {
            return orders.stream()
                    .filter(order -> order.getUser().equals(user))
                    .map(orderMapper::mapToRecord)
                    .toList();
        }
        return orders.stream()
                .map(orderMapper::mapToRecord)
                .toList();
    }

    @Override
    public List<OrderRecord> ordersByUserId(long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::mapToRecord)
                .toList();
    }

    @Override
    public OrderRecord getOrderById(long orderId, User user) {
        Order order = orderRepository.findById(orderId);
        if (user.isNotAdmin() && !order.getUser().equals(user)) return null;
        return orderMapper.mapToRecord(order);
    }

    @Override
    public List<ProductLineRecord> getOrderProducts(long orderId, User user) {
        Order order = orderRepository.findById(orderId);
        if (user.isNotAdmin() && order.getUser().equals(user)) throw new NotAuthorizedException();
        return order.getProductLines()
                .stream()
                .map(productLineMapper::mapToRecord)
                .toList();
    }

    @Override
    public boolean deleteOrder(long orderId, User user) {
        Order order = orderRepository.findById(orderId);
        if (user.isNotAdmin() && order.getUser().equals(user)) throw new NotAuthorizedException();
        orderRepository.delete(order);
        return true;
    }

    private void processProductLine(@NonNull ProductLine productLine) {
        Product product = productLine.getProduct();
        product.sell(productLine.getQuantity());
        productRepository.save(product);
    }

}
