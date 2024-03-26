package dev.attara.stockify.application.service;

import dev.attara.stockify.application.dto.input.CreateProductDTO;
import dev.attara.stockify.application.dto.input.UpdateProductDTO;
import dev.attara.stockify.application.dto.output.ProductRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductRecord createProduct(CreateProductDTO createProductDTO);
    ProductRecord updateProduct(long productId, UpdateProductDTO updateProductDTO);
    ProductRecord getProductById(long productId);
    List<ProductRecord> getAllProducts();
    boolean deleteProduct(long productId);
    List<ProductRecord> getLowStockProducts(int threshold);
}
