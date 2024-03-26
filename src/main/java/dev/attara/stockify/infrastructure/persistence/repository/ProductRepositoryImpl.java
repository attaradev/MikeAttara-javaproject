package dev.attara.stockify.infrastructure.persistence.repository;

import dev.attara.stockify.domain.exception.ProductNotFoundException;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.entity.ProductEntity;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final static int MINIMUM_THRESHOLD = 1;

    private final ProductMapper productMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product findById(long id) throws ProductNotFoundException {
        try {
            ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
            return productMapper.toModel(productEntity);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
    }

    @Override
    public List<Product> findLowStockProducts(int threshold) {
        if (threshold <= 0) threshold = MINIMUM_THRESHOLD;
        return entityManager.createQuery("SELECT p FROM ProductEntity p WHERE p.stock < :threshold", ProductEntity.class)
                .setParameter("threshold", threshold)
                .getResultList()
                .stream()
                .map(productMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class)
                .getResultList()
                .stream()
                .map(productMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        entityManager.merge(productMapper.toEntity(product));
    }

    @Override
    public void delete(Product product) {
        entityManager.remove(productMapper.toEntity(product));
    }

}
