package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dto.OrderRecord;
import dev.attara.stockify.application.dto.ProductLineRecord;
import dev.attara.stockify.application.security.AuthenticatedUserProvider;
import dev.attara.stockify.application.service.ordermanagement.createorder.CreateOrder;
import dev.attara.stockify.application.service.ordermanagement.createorder.CreateOrderHandler;
import dev.attara.stockify.application.service.ordermanagement.createorder.ProductLineData;
import dev.attara.stockify.application.service.ordermanagement.deleteorder.DeleteOrder;
import dev.attara.stockify.application.service.ordermanagement.deleteorder.DeleteOrderHandler;
import dev.attara.stockify.application.service.ordermanagement.getallorders.GetAllOrders;
import dev.attara.stockify.application.service.ordermanagement.getallorders.GetAllOrdersHandler;
import dev.attara.stockify.application.service.ordermanagement.getorder.GetOrder;
import dev.attara.stockify.application.service.ordermanagement.getorder.GetOrderHandler;
import dev.attara.stockify.application.service.ordermanagement.getorderproducts.GetOrderProducts;
import dev.attara.stockify.application.service.ordermanagement.getorderproducts.GetOrderProductsHandler;
import dev.attara.stockify.application.service.ordermanagement.getordersbyuser.GetOrdersByUser;
import dev.attara.stockify.application.service.ordermanagement.getordersbyuser.GetOrdersByUserHandler;
import dev.attara.stockify.application.service.ordermanagement.updateorder.UpdateOrder;
import dev.attara.stockify.application.service.ordermanagement.updateorder.UpdateOrderHandler;
import dev.attara.stockify.domain.exception.NotAuthorizedException;
import dev.attara.stockify.domain.model.User;
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

    @MutationMapping
    public OrderRecord createOrder(@Argument List<ProductLineData> productLines, Principal principal) {
        try {
            return createOrderHandler.handle(new CreateOrder(productLines, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while creating order: {}", e.getMessage(), e);
            throw e;
        }
    }

    @MutationMapping
    public OrderRecord updateOrder(@Argument long orderId, @Argument List<ProductLineData> productLines, Principal principal) {
        try {
            return updateOrderHandler.handle(new UpdateOrder(orderId, productLines, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while updating order: {}", e.getMessage(), e);
            throw e;
        }
    }

    @MutationMapping
    public boolean deleteOrder(@Argument long orderId, Principal principal) {
        try {
            return deleteOrderHandler.handle(new DeleteOrder(orderId, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while deleting order: {}", e.getMessage(), e);
            throw e;
        }
    }

    @QueryMapping
    public OrderRecord order(@Argument long orderId, Principal principal) {
        try {
            return getOrderHandler.handle(new GetOrder(orderId, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while fetching order: {}", e.getMessage(), e);
            throw e;
        }
    }

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

    @QueryMapping
    public List<OrderRecord> ordersByUser(@Argument long userId, Principal principal) {
        try {
            User user = userProvider.get(principal);
            if (user.isNotAdmin() && user.getId() != userId) throw new NotAuthorizedException();
            return getOrdersByUserHandler.handle(new GetOrdersByUser(userId));
        } catch (Exception e) {
            logger.error("Error occurred while fetching orders by user: {}", e.getMessage(), e);
            throw e;
        }
    }

    @QueryMapping
    public List<ProductLineRecord> productsInOrder(@Argument long orderId, Principal principal) {
        try {
            return getOrderProductsHandler.handle(new GetOrderProducts(orderId, userProvider.get(principal)));
        } catch (Exception e) {
            logger.error("Error occurred while fetching products in order: {}", e.getMessage(), e);
            throw e;
        }
    }
}
