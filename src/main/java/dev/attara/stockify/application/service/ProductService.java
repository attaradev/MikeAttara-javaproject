package dev.attara.stockify.application.service;

import dev.attara.stockify.application.dto.input.CreateProductDTO;
import dev.attara.stockify.application.dto.input.UpdateProductDTO;
import dev.attara.stockify.application.dto.output.ProductRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductRecord createProduct(CreateProductDTO createProductDTO);
    ProductRecord updateProduct(Long productId, UpdateProductDTO updateProductDTO);
    ProductRecord getProductById(Long productId);
    List<ProductRecord> getAllProducts();
    boolean deleteProduct(Long productId);
    List<ProductRecord> getLowStockProducts(int threshold);
}
