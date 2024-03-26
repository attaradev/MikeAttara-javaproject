package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dto.input.CreateOrderDTO;
import dev.attara.stockify.application.dto.input.ProductLineDTO;
import dev.attara.stockify.application.dto.output.OrderRecord;
import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.application.security.AuthenticatedUserProvider;
import dev.attara.stockify.application.service.OrderService;
import dev.attara.stockify.domain.exception.NotAuthorizedException;
import dev.attara.stockify.domain.model.User;
import lombok.RequiredArgsConstructor;
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

    private final OrderService orderService;

    private final AuthenticatedUserProvider authenticatedUserProvider;

    @MutationMapping
    public OrderRecord createOrder(@Argument List<ProductLineDTO> productLines, Principal principal) {
        return orderService.createOrder(new CreateOrderDTO(productLines, authenticatedUserProvider.user(principal)));
    }

    @MutationMapping
    public OrderRecord updateOrder(@Argument long id, @Argument List<ProductLineDTO> productLines, Principal principal) {
        return orderService.updateOrder(id, productLines, authenticatedUserProvider.user(principal));
    }

    @MutationMapping
    public boolean deleteOrder(@Argument long id, Principal principal) {
        return orderService.deleteOrder(id, authenticatedUserProvider.user(principal));
    }

    @QueryMapping
    public OrderRecord order(@Argument long id, Principal principal) {
        return orderService.getOrderById(id, authenticatedUserProvider.user(principal));
    }

    @QueryMapping
    public List<OrderRecord> orders(Principal principal) {
        return orderService.allOrders(authenticatedUserProvider.user(principal));
    }

    @QueryMapping
    public List<OrderRecord> ordersByUser(@Argument long userId, Principal principal) {
        User user = authenticatedUserProvider.user(principal);
        if (user.isNotAdmin() && user.getId() != userId) throw new NotAuthorizedException();
        return orderService.ordersByUserId(userId);
    }

    @QueryMapping
    public List<ProductLineRecord> productsInOrder(@Argument long orderId, Principal principal) {
        return orderService.getOrderProducts(orderId, authenticatedUserProvider.user(principal));
    }

}
