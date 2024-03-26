package dev.attara.stockify.application.service.impl;

import dev.attara.stockify.application.dto.input.CreateProductDTO;
import dev.attara.stockify.application.dto.input.UpdateProductDTO;
import dev.attara.stockify.application.dto.output.ProductRecord;
import dev.attara.stockify.application.service.ProductService;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductRecord createProduct(CreateProductDTO dto) {
        Product product = Product.create(
                productRepository.nextId(),
                dto.name(),
                dto.price(),
                dto.stock()
        );
        productRepository.save(product);
        return productMapper.mapToRecord(product);
    }

    @Override
    public ProductRecord updateProduct(long productId, UpdateProductDTO dto) {
        Product product = productRepository.findById(productId);
        String name = dto.name();
        double price = dto.price();
        int stock = dto.stock();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        productRepository.save(product);
        return productMapper.mapToRecord(product);
    }

    @Override
    public ProductRecord getProductById(long productId) {
        Product product = productRepository.findById(productId);
        return productMapper.mapToRecord(product);
    }

    @Override
    public List<ProductRecord> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().
                map(productMapper::mapToRecord)
                .toList();
    }

    @Override
    public boolean deleteProduct(long productId) {
        Product product = productRepository.findById(productId);
        productRepository.delete(product);
        return true;
    }

    @Override
    public List<ProductRecord> getLowStockProducts(int threshold) {
        List<Product> products = productRepository.findLowStockProducts(threshold);
        return products.stream()
                .map(productMapper::mapToRecord)
                .toList();
    }

}
