// ProductRepository.java
package dev.attara.stockify.domain.repository;

import dev.attara.stockify.domain.exception.ProductNotFoundException;
import dev.attara.stockify.domain.model.Product;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    Product findById(Long id) throws ProductNotFoundException;

    List<Product> findLowStockProducts(int threshold);
}
