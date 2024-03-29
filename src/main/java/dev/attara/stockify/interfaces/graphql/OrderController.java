package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dtos.OrderRecord;
import dev.attara.stockify.application.dtos.ProductLineRecord;
import dev.attara.stockify.infrastructure.security.AuthenticatedUserProvider;
import dev.attara.stockify.application.services.ordermanagement.createorder.CreateOrder;
import dev.attara.stockify.application.services.ordermanagement.createorder.CreateOrderHandler;
import dev.attara.stockify.application.services.ordermanagement.createorder.ProductLineData;
import dev.attara.stockify.application.services.ordermanagement.deleteorder.DeleteOrder;
import dev.attara.stockify.application.services.ordermanagement.deleteorder.DeleteOrderHandler;
import dev.attara.stockify.application.services.ordermanagement.getallorders.GetAllOrders;
import dev.attara.stockify.application.services.ordermanagement.getallorders.GetAllOrdersHandler;
import dev.attara.stockify.application.services.ordermanagement.getorder.GetOrder;
import dev.attara.stockify.application.services.ordermanagement.getorder.GetOrderHandler;
import dev.attara.stockify.application.services.ordermanagement.getorderproducts.GetOrderProducts;
import dev.attara.stockify.application.services.ordermanagement.getorderproducts.GetOrderProductsHandler;
import dev.attara.stockify.application.services.ordermanagement.getordersbyuser.GetOrdersByUser;
import dev.attara.stockify.application.services.ordermanagement.getordersbyuser.GetOrdersByUserHandler;
import dev.attara.stockify.application.services.ordermanagement.updateorder.UpdateOrder;
import dev.attara.stockify.application.services.ordermanagement.updateorder.UpdateOrderHandler;
import dev.attara.stockify.domain.exceptions.NotAuthorizedException;
import dev.attara.stockify.domain.models.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

/**
 * Controller responsible for handling GraphQL queries and mutations related to orders.
 * This controller manages operations such as creating, updating, deleting, and retrieving orders and order-related data.
 */
@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final AuthenticatedUserProvider userProvider;

    private final CreateOrderHandler createOrderHandler;

    private final GetOrderHandler getOrderHandler;

    private final GetOrdersByUserHandler getOrdersByUserHandler;

    private final UpdateOrderHandler updateOrderHandler;

    private final DeleteOrderHandler deleteOrderHandler;

    private final GetAllOrdersHandler getAllOrdersHandler;

    private final GetOrderProductsHandler getOrderProductsHandler;

    /**
     * Creates a new order.
     *
     * @param productLines The list of product lines in the order.
     * @param principal    The principal object representing the authenticated user.
     * @return The order record representing the created order.
     */
    @MutationMapping
    public OrderRecord createOrder(@Argument List<ProductLineData> productLines, Principal principal) {
        try {
            return createOrderHandler.handle(new CreateOrder(productLines, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while creating order: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Updates an existing order.
     *
     * @param orderId      The ID of the order to update.
     * @param productLines The updated list of product lines in the order.
     * @param principal    The principal object representing the authenticated user.
     * @return The order record representing the updated order.
     */
    @MutationMapping
    public OrderRecord updateOrder(@Argument @NonNull String orderId, @Argument List<ProductLineData> productLines, Principal principal) {
        try {
            return updateOrderHandler.handle(new UpdateOrder(orderId, productLines, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while updating order: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Deletes an existing order.
     *
     * @param orderId   The ID of the order to delete.
     * @param principal The principal object representing the authenticated user.
     * @return true if the order was successfully deleted, false otherwise.
     */
    @MutationMapping
    public boolean deleteOrder(@Argument @NonNull String orderId, Principal principal) {
        try {
            return deleteOrderHandler.handle(new DeleteOrder(orderId, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while deleting order: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Retrieves details of a specific order.
     *
     * @param orderId   The ID of the order to retrieve.
     * @param principal The principal object representing the authenticated user.
     * @return The order record representing the retrieved order.
     */
    @QueryMapping
    public OrderRecord order(@Argument @NonNull String orderId, Principal principal) {
        try {
            return getOrderHandler.handle(new GetOrder(orderId, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while fetching order: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Retrieves all orders.
     *
     * @param principal The principal object representing the authenticated user.
     * @return The list of order records representing all orders.
     */
    @QueryMapping
    public List<OrderRecord> orders(Principal principal) {
        try {
            User user = userProvider.get(principal);
            if (user.isNotAdmin()) {
                return getOrdersByUserHandler.handle(new GetOrdersByUser(user.getId()));
            }
            return getAllOrdersHandler.handle(new GetAllOrders());
        } catch (Exception e) {
            logger.error("Error occurred while fetching orders: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Retrieves orders associated with a specific user.
     *
     * @param userId    The ID of the user whose orders are to be retrieved.
     * @param principal The principal object representing the authenticated user.
     * @return The list of order records representing orders associated with the specified user.
     */
    @QueryMapping
    public List<OrderRecord> ordersByUser(@Argument @NonNull String userId, Principal principal) {
        try {
            User user = userProvider.get(principal);
            if (user.isNotAdmin() && user.getId().equals(userId)) throw new NotAuthorizedException();
            return getOrdersByUserHandler.handle(new GetOrdersByUser(userId));
        } catch (Exception e) {
            logger.error("Error occurred while fetching orders by user: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Retrieves products associated with a specific order.
     *
     * @param orderId   The ID of the order whose products are to be retrieved.
     * @param principal The principal object representing the authenticated user.
     * @return The list of product line records representing products associated with the specified order.
     */
    @QueryMapping
    public List<ProductLineRecord> productsInOrder(@Argument @NonNull String orderId, Principal principal) {
        try {
            return getOrderProductsHandler.handle(new GetOrderProducts(orderId, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while fetching products in order: {}", e.getMessage(), e);
            throw e;
        }
    }
}
