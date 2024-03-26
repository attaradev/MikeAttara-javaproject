package dev.attara.stockify.infrastructure.persistence.repository;

import dev.attara.stockify.domain.exception.OrderNotFoundException;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.repository.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.entity.OrderEntity;
import dev.attara.stockify.infrastructure.persistence.entity.ProductLineEntity;
import dev.attara.stockify.infrastructure.persistence.mapper.OrderMapper;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
import dev.attara.stockify.infrastructure.persistence.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper orderMapper;

    private final ProductMapper productMapper;

    private final UserMapper userMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findById(long id) throws OrderNotFoundException {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, id);
        if (orderEntity == null) {
            throw new OrderNotFoundException(id);
        }
        return orderMapper.mapToDomain(orderEntity);
    }

    @Override
    public List<Order> findAll() {
        return entityManager.createQuery("SELECT o FROM OrderEntity o", OrderEntity.class)
                .getResultList()
                .stream()
                .map(orderMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return entityManager.createQuery(
                        "SELECT o FROM OrderEntity o WHERE o.user.id = :userId", OrderEntity.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .map(orderMapper::mapToDomain)
                .collect(Collectors.toList());
    }

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
    }


    @Override
    public void delete(Order order) {
        entityManager.remove(orderMapper.mapToEntity(order));
    }


    private void saveProductLine(ProductLineEntity productLineEntity){
        entityManager.merge(productLineEntity);
    }

}
