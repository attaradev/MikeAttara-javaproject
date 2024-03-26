package dev.attara.stockify.domain.repository;

import dev.attara.stockify.domain.exception.OrderNotFoundException;
import dev.attara.stockify.domain.model.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    Order findById(long id) throws OrderNotFoundException;
    List<Order> findByUserId(long userId);
}
