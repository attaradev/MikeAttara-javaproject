package dev.attara.stockify.domain.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductLineTest {

    @Test
    void testCreateProductLine() {
        Product product = Product.create("1", "Test Product", 10.0, 10);
        ProductLine productLine = ProductLine.create(product, 5);
        assertNotNull(productLine);
        assertEquals(product, productLine.getProduct());
        assertEquals(5, productLine.getQuantity());
    }

    @Test
    void testSetProduct() {
        Product product1 = Product.create("1", "Test Product 1", 10.0, 10);
        Product product2 = Product.create("2", "Test Product 2", 20, 20);
        ProductLine productLine = ProductLine.create(product1, 5);
        productLine.setProduct(product2);
        assertEquals(product2, productLine.getProduct());
    }

    @Test
    void testSetQuantity() {
        Product product = Product.create("1", "Test Product", 10.0, 10);
        ProductLine productLine = ProductLine.create(product, 5);
        productLine.setQuantity(8);
        assertEquals(8, productLine.getQuantity());
    }

    @Test
    void testEqualsSameObject() {
        Product product = Product.create("1", "Test Product", 10.0, 10);
        ProductLine productLine1 = ProductLine.create(product, 5);
        assertEquals(productLine1, productLine1);
    }

    @Test
    void testEqualsDifferentObjects() {
        Product product1 = Product.create("1", "Test Product 1", 10.0, 10);
        Product product2 = Product.create("2", "Test Product 2", 20.0, 20);
        assertNotEquals(ProductLine.create(product1, 5), ProductLine.create(product2, 5));
    }

    @Test
    void testEqualsNull() {
        Product product = Product.create("1", "Test Product", 10.0, 10);
        ProductLine productLine = ProductLine.create(product, 5);
        assertNotEquals(null, productLine);
    }

    @Test
    void testEqualsDifferentClass() {
        Product product = Product.create("1", "Test Product", 10.0, 10);
        ProductLine productLine = ProductLine.create(product, 5);
        assertNotEquals(productLine, new Object());
    }
}
