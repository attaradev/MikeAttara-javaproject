package dev.attara.stockify.domain.model;

import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void testCreateOrder() {
        List<ProductLine> productLines = new ArrayList<>();
        productLines.add(ProductLine.create(Product.create("1", "Product 1", 10.0, 10), 10));
        productLines.add(ProductLine.create(Product.create("2", "Product 2", 20.0, 20), 20));

        User user = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        Order order = Order.create("1", user, productLines);

        assertNotNull(order);
        assertEquals("1", order.getId());
        assertEquals(user, order.getUser());
        assertEquals(productLines, order.getProductLines());
    }

    @Test
    void testCreateOrderWithInvalidId() {
        List<ProductLine> productLines = new ArrayList<>();
        User user = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        assertThrows(IllegalArgumentException.class, () -> Order.create(null, user, productLines));
    }

    @Test
    void testCreateOrderWithEmptyProductLines() {
        List<ProductLine> productLines = new ArrayList<>();
        User user = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        assertThrows(IllegalArgumentException.class, () -> Order.create("1", user, productLines));
    }

    @Test
    void testAddProductLine() {
        User user = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        Product product = Product.create("1", "Product 1", 10.0, 10);
        ProductLine productLine1 = ProductLine.create(product, 3);
        List<ProductLine> productLineList = new ArrayList<>();
        productLineList.add(productLine1);
        Order order = Order.create("1", user, productLineList);
        ProductLine productLine = getProductLine(product);
        order.addProductLine(productLine);
        assertEquals(1, order.getProductLines().size());
        assertEquals(8, order.getProductLines().get(0).getQuantity());
    }

    @NonNull
    private static ProductLine getProductLine(Product product) {
        return ProductLine.create(product, 5);

    }

    @Test
    void testAddProductLineExisting() {
        User user = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        Product product = Product.create("1", "Product 1", 10.0, 10);
        ProductLine existingProductLine = getProductLine(product);
        List<ProductLine> productLines = new ArrayList<>();
        productLines.add(existingProductLine);
        Order order = Order.create("1", user, productLines);

        ProductLine newProductLine = ProductLine.create(product, 5);
        order.addProductLine(newProductLine);
        assertEquals(1, order.getProductLines().size());
        assertEquals(10, order.getProductLines().get(0).getQuantity());
    }
}
