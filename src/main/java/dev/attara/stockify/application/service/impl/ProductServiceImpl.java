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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductRecord createProduct(CreateProductDTO createProductDTO) {
        Product product = new Product(
                productRepository.nextId(),
                createProductDTO.name(),
                createProductDTO.stock(),
                createProductDTO.price()
        );
        productRepository.save(product);
        return productMapper.toRecord(product);
    }

    @Override
    public ProductRecord updateProduct(Long productId, UpdateProductDTO updateProductDTO) {
        Product product = productRepository.findById(productId);
        String name = updateProductDTO.name();
        double price = updateProductDTO.price();
        int stock = updateProductDTO.stock();
        if(name != null) product.setName(name);
        if (price > 0) product.setPrice(price);
        if (stock > 0) product.setStock(stock);
        productRepository.save(product);
        return productMapper.toRecord(product);
    }

    @Override
    public ProductRecord getProductById(Long productId) {
        Product product = productRepository.findById(productId);
        return productMapper.toRecord(product);
    }

    @Override
    public List<ProductRecord> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public boolean deleteProduct(Long productId) {
        Product product = productRepository.findById(productId);
        productRepository.delete(product);
        return true;
    }

    @Override
    public List<ProductRecord> getLowStockProducts(int threshold) {
        List<Product> products = productRepository.findLowStockProducts(threshold);
        return products.stream().map(productMapper::toRecord).collect(Collectors.toList());
    }
}
