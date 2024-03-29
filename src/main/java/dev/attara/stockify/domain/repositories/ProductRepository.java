package dev.attara.stockify.domain.repositories;

import dev.attara.stockify.domain.exceptions.ProductNotFoundException;
import dev.attara.stockify.domain.models.Product;

import java.util.List;

/**
 * Repository interface for managing products.
 */
public interface ProductRepository extends BaseRepository<Product> {

    /**
     * Finds and returns a product by its ID.
     *
     * @param id the ID of the product to find
     * @return the found product
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    Product findById(String id) throws ProductNotFoundException;

    /**
     * Finds and returns all products with stock below the specified threshold.
     *
     * @param threshold the threshold value for low stock
     * @return a list of products with low stock
     */
    List<Product> findLowStockProducts(int threshold);
}
