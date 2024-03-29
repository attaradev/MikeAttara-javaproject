package dev.attara.stockify.infrastructure.persistence.repositories;

import dev.attara.stockify.domain.exceptions.OrderNotFoundException;
import dev.attara.stockify.domain.models.Order;
import dev.attara.stockify.domain.repositories.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.entities.OrderEntity;
import dev.attara.stockify.infrastructure.persistence.entities.ProductLineEntity;
import dev.attara.stockify.infrastructure.persistence.mappers.OrderMapper;
import dev.attara.stockify.infrastructure.persistence.mappers.ProductMapper;
import dev.attara.stockify.infrastructure.persistence.mappers.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the OrderRepository interface for accessing and managing OrderEntity objects in the database.
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper orderMapper;

    private final ProductMapper productMapper;

    private final UserMapper userMapper;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return The order with the specified ID.
     * @throws OrderNotFoundException If no order exists with the given ID.
     */
    @Override
    public Order findById(String id) throws OrderNotFoundException {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, id);
        if (orderEntity == null) {
            throw new OrderNotFoundException(id);
        }
        return orderMapper.mapToDomain(orderEntity);
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return A list of all orders.
     */
    @Override
    public List<Order> findAll() {
        return entityManager.createQuery("SELECT o FROM OrderEntity o", OrderEntity.class)
                .getResultList()
                .stream()
                .map(orderMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves orders associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of orders associated with the user.
     */
    @Override
    public List<Order> findByUserId(String userId) {
        return entityManager.createQuery(
                        "SELECT o FROM OrderEntity o WHERE o.user.id = :userId", OrderEntity.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .map(orderMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    /**
     * Saves an order to the database.
     *
     * @param order The order to save.
     */
    @Override
    public void save(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setUser(userMapper.mapToEntity(order.getUser()));
        entityManager.merge(orderEntity);
        List<ProductLineEntity> productLineEntities = order.getProductLines().stream().map(productLine -> {
            ProductLineEntity productLineEntity = new ProductLineEntity();
            productLineEntity.setProduct(productMapper.mapToEntity(productLine.getProduct()));
            productLineEntity.setOrder(orderEntity);
            productLineEntity.setQuantity(productLine.getQuantity());
            return productLineEntity;
        }).toList();
        productLineEntities.forEach(this::saveProductLine);
        entityManager.flush();
    }

    /**
     * Deletes an order from the database.
     *
     * @param order The order to delete.
     */
    @Override
    public void delete(Order order) {
        OrderEntity managedOrder = entityManager.find(OrderEntity.class, order.getId());
        entityManager.remove(managedOrder);
    }

    private void saveProductLine(ProductLineEntity productLineEntity){
        entityManager.merge(productLineEntity);
    }
}
