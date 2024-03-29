package dev.attara.stockify.infrastructure.persistence.repository;

import dev.attara.stockify.domain.exception.ProductNotFoundException;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.entity.ProductEntity;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductRepository interface for accessing and managing ProductEntity objects in the database.
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final static int MINIMUM_THRESHOLD = 1;

    private final ProductMapper productMapper;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id The unique identifier of the product.
     * @return The product with the specified ID.
     * @throws ProductNotFoundException If no product exists with the given ID.
     */
    @Override
    public Product findById(String id) throws ProductNotFoundException {
        try {
            ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
            return productMapper.mapToDomain(productEntity);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
    }

    /**
     * Retrieves products with stock levels below a specified threshold.
     *
     * @param threshold The minimum stock threshold.
     * @return A list of products with stock levels below the threshold.
     */
    @Override
    public List<Product> findLowStockProducts(int threshold) {
        if (threshold <= 0) threshold = MINIMUM_THRESHOLD;
        return entityManager.createQuery("SELECT p FROM ProductEntity p WHERE p.stock < :threshold", ProductEntity.class)
                .setParameter("threshold", threshold)
                .getResultList()
                .stream()
                .map(productMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all products from the database.
     *
     * @return A list of all products.
     */
    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class)
                .getResultList()
                .stream()
                .map(productMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    /**
     * Saves a product to the database.
     *
     * @param product The product to save.
     */
    @Override
    public void save(Product product) {
        entityManager.merge(productMapper.mapToEntity(product));
        entityManager.flush();
    }

    /**
     * Deletes a product from the database.
     *
     * @param product The product to delete.
     */
    @Override
    public void delete(Product product) {
        ProductEntity managedProduct = entityManager.find(ProductEntity.class, product.getId());
        entityManager.remove(managedProduct);
    }

}
