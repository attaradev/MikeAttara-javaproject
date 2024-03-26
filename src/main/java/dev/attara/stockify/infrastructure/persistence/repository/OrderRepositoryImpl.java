package dev.attara.stockify.infrastructure.persistence.repository;

import dev.attara.stockify.domain.exception.OrderNotFoundException;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.repository.OrderRepository;
import dev.attara.stockify.infrastructure.persistence.entity.OrderEntity;
import dev.attara.stockify.infrastructure.persistence.mapper.OrderMapper;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findById(long id) throws OrderNotFoundException {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, id);
        if (orderEntity == null) {
            throw new OrderNotFoundException(id);
        }
        return orderMapper.toModel(orderEntity);
    }

    @Override
    public List<Order> findAll() {
        return entityManager.createQuery("SELECT o FROM OrderEntity o", OrderEntity.class)
                .getResultList()
                .stream()
                .map(orderMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return entityManager.createQuery(
                        "SELECT o FROM OrderEntity o WHERE o.user.id = :userId", OrderEntity.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .map(orderMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Order order) {
        entityManager.merge(orderMapper.toEntity(order));
    }


    @Override
    public void delete(Order order) {
        entityManager.remove(orderMapper.toEntity(order));
    }

}
