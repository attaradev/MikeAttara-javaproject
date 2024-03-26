package dev.attara.stockify.domain.service;

import dev.attara.stockify.application.service.ordermanagement.createorder.ProductLineData;
import dev.attara.stockify.domain.model.Order;
import dev.attara.stockify.domain.model.ProductLine;
import dev.attara.stockify.domain.model.User;

import java.util.List;

public interface OrderService {

    /**
     * Creates a new order with the specified product lines for the given user.
     *
     * @param productLines the list of product lines to be included in the order
     * @param user         the user who is placing the order
     * @return the created order
     */
    Order createOrder(List<ProductLine> productLines, User user);

    /**
     * Updates an existing order with the specified product lines.
     *
     * @param orderId      the ID of the order to be updated
     * @param productLines the list of product lines to be updated in the order
     * @return the updated order
     */
    Order updateOrder(long orderId, List<ProductLine> productLines);

    /**
     * Creates a new product line based on the provided product line data.
     *
     * @param data the data for creating the product line
     * @return the created product line
     */
    ProductLine createProductLine(ProductLineData data);

}
