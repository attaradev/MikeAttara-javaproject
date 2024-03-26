package dev.attara.stockify.application.service.impl;

import dev.attara.stockify.application.dto.input.CreateProductDTO;
import dev.attara.stockify.application.dto.input.UpdateProductDTO;
import dev.attara.stockify.application.dto.output.ProductRecord;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        // Arrange
        long id = 1L;
        CreateProductDTO createProductDTO = new CreateProductDTO("Product 1", 100, 10.0);
        Product product = Product.create(id, createProductDTO.name(), createProductDTO.price(), createProductDTO.stock());
        ProductRecord expectedProductRecord = new ProductRecord(product.getId(), product.getName(), product.getStock(), product.getPrice());
        when(productRepository.nextId()).thenReturn(id);
        when(productMapper.mapToRecord(product)).thenReturn(expectedProductRecord);

        // Act
        ProductRecord actualProductRecord = productService.createProduct(createProductDTO);

        // Assert
        assertEquals(expectedProductRecord, actualProductRecord);
        verify(productRepository, times(1)).nextId(); // Verify nextId() is called
        verify(productRepository, times(1)).save(product); // Verify save() is called with the same product instance
    }


    @Test
    void testUpdateProduct() {
        // Arrange
        long productId = 1L;
        UpdateProductDTO updateProductDTO = new UpdateProductDTO("Updated Product", 150, 15.0);
        Product product = Product.create(productId, "Product 1", 10.0, 100);
        when(productRepository.findById(productId)).thenReturn(product);
        when(productMapper.mapToRecord(product)).thenReturn(new ProductRecord(productId, "Updated Product", 150, 15.0));

        // Act
        ProductRecord updatedProductRecord = productService.updateProduct(productId, updateProductDTO);

        // Assert
        assertEquals(updateProductDTO.name(), updatedProductRecord.name());
        assertEquals(updateProductDTO.price(), updatedProductRecord.price());
        assertEquals(updateProductDTO.stock(), updatedProductRecord.stock());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetProductById() {
        // Arrange
        long productId = 1L;
        Product product = Product.create(productId, "Product 1", 10.0, 100);
        when(productRepository.findById(productId)).thenReturn(product);
        ProductRecord expectedProductRecord = new ProductRecord(productId, "Product 1", 100, 10.0);
        when(productMapper.mapToRecord(product)).thenReturn(expectedProductRecord);

        // Act
        ProductRecord actualProductRecord = productService.getProductById(productId);

        // Assert
        assertEquals(expectedProductRecord, actualProductRecord);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        Product product1 = Product.create(1L, "Product 1", 10.0, 100);
        Product product2 = Product.create(2L, "Product 2", 20.0, 200);
        productList.add(product1);
        productList.add(product2);
        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.mapToRecord(product1)).thenReturn(new ProductRecord(product1.getId(), product1.getName(), product1.getStock(), product1.getPrice()));
        when(productMapper.mapToRecord(product2)).thenReturn(new ProductRecord(product2.getId(), product2.getName(), product2.getStock(), product2.getPrice()));

        // Act
        List<ProductRecord> actualProductRecords = productService.getAllProducts();

        // Assert
        List<ProductRecord> expectedProductRecords = new ArrayList<>();
        for (Product product : productList) {
            expectedProductRecords.add(new ProductRecord(product.getId(), product.getName(), product.getStock(),product.getPrice()));
        }
        assertEquals(expectedProductRecords, actualProductRecords);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        long productId = 1L;
        Product product = Product.create(productId, "Product 1", 10.0, 100);
        when(productRepository.findById(productId)).thenReturn(product);

        // Act
        boolean result = productService.deleteProduct(productId);

        // Assert
        assertTrue(result);
        verify(productRepository, times(1)).delete(product);
    }
}
