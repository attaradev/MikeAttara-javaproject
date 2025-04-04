package dev.attara.stockify.domain.models;

import dev.attara.stockify.domain.exceptions.InsufficientStockException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void testCreateProduct() {
        Product product = Product.create("233ww", "Test Product", 10.0, 100);
        assertNotNull(product);
        assertEquals("233ww", product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals(100, product.getStock());
    }

    @Test
    void testCreateProductWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> Product.create(null, "Test Product", 10.0, 100));
    }

    @Test
    void testCreateProductWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> Product.create("1", "Test Product", -10.0, 100));
    }

    @Test
    void testCreateProductWithNegativeStock() {
        assertThrows(IllegalArgumentException.class, () -> Product.create("1", "Test Product", 10.0, -100));
    }

    @Test
    void testSetName() {
        Product product = Product.create("1", "Test Product", 10.0, 100);
        product.setName("Updated Product");
        assertEquals("Updated Product", product.getName());
    }

    @Test
    void testSetPrice() {
        Product product = Product.create("1", "Test Product", 10.0, 100);
        product.setPrice(15.0);
        assertEquals(15.0, product.getPrice());
    }

    @Test
    void testSetStock() {
        Product product = Product.create("1", "Test Product", 10.0, 100);
        product.setStock(200);
        assertEquals(200, product.getStock());
    }

    @Test
    void testSellProduct() {
        Product product = Product.create("1", "Test Product", 10.0, 100);
        assertDoesNotThrow(() -> product.sell(50));
        assertEquals(50, product.getStock());
    }

    @Test
    void testSellProductInsufficientStock() {
        Product product = Product.create("1", "Test Product", 10.0, 100);
        assertThrows(InsufficientStockException.class, () -> product.sell(200));
    }
}
