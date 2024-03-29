package dev.attara.stockify.domain.services.impl;

import dev.attara.stockify.application.services.ordermanagement.createorder.ProductLineData;
import dev.attara.stockify.domain.models.Order;
import dev.attara.stockify.domain.models.Product;
import dev.attara.stockify.domain.models.ProductLine;
import dev.attara.stockify.domain.models.User;
import dev.attara.stockify.domain.repositories.OrderRepository;
import dev.attara.stockify.domain.repositories.ProductRepository;
import dev.attara.stockify.domain.services.OrderManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link OrderManager} interface.
 * Handles order-related operations such as creating and updating orders.
 */
@Service
@RequiredArgsConstructor
public class OrderManagerImpl implements OrderManager {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final IDGenerator idGenerator;

    /**
     * Creates a new order with the given list of product lines for the specified user.
     * Validates and processes the product lines before saving the order.
     *
     * @param productLines The list of product lines to be included in the order.
     * @param user         The user who is placing the order.
     * @return The newly created order.
     */
    @Override
    @Transactional
    public Order createOrder(List<ProductLine> productLines, User user) {
        Order order = Order.create(idGenerator.generateID(), user, productLines);
        validateAndProcessProductLines(productLines);
        orderRepository.save(order);
        return order;
    }

    /**
     * Updates an existing order with the specified ID by replacing its product lines with the given ones.
     * Validates and processes the new product lines before updating the order.
     *
     * @param orderId      The ID of the order to be updated.
     * @param productLines The new list of product lines for the order.
     * @return The updated order.
     */
    @Override
    @Transactional
    public Order updateOrder(String orderId, List<ProductLine> productLines) {
        Order order = orderRepository.findById(orderId);
        validateAndProcessProductLines(productLines);
        productLines.forEach(order::addProductLine);
        orderRepository.save(order);
        return order;
    }

    /**
     * Creates a new product line based on the given product line data.
     *
     * @param data The data containing information about the product line.
     * @return The newly created product line.
     */
    @Override
    public ProductLine createProductLine(ProductLineData data) {
        Product product = productRepository.findById(data.productId());
        return ProductLine.create(product, data.quantity());
    }

    /**
     * Validates and processes the given list of product lines.
     * For each product line, it deducts the sold quantity from the corresponding product's stock.
     *
     * @param productLines The list of product lines to be validated and processed.
     */
    private void validateAndProcessProductLines(@NonNull List<ProductLine> productLines) {
        for (ProductLine productLine : productLines) {
            Product product = productLine.getProduct();
            product.sell(productLine.getQuantity());
            productRepository.save(product);
        }
    }
}
