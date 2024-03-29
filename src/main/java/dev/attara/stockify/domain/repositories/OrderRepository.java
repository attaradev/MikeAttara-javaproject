package dev.attara.stockify.domain.repositories;

import dev.attara.stockify.domain.exceptions.OrderNotFoundException;
import dev.attara.stockify.domain.models.Order;

import java.util.List;

/**
 * Repository interface for managing orders.
 */
public interface OrderRepository extends BaseRepository<Order> {

    /**
     * Finds and returns an order by its ID.
     *
     * @param id the ID of the order to find
     * @return the found order
     * @throws OrderNotFoundException if the order with the given ID is not found
     */
    Order findById(String id) throws OrderNotFoundException;

    /**
     * Finds and returns all orders associated with the given user ID.
     *
     * @param userId the ID of the user
     * @return a list of orders associated with the user
     */
    List<Order> findByUserId(String userId);
}
