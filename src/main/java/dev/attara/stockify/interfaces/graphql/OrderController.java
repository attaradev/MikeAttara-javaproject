package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dto.input.CreateOrderDTO;
import dev.attara.stockify.application.dto.input.ProductLineDTO;
import dev.attara.stockify.application.dto.input.UpdateProductDTO;
import dev.attara.stockify.application.dto.output.OrderRecord;
import dev.attara.stockify.application.dto.output.ProductLineRecord;
import dev.attara.stockify.application.dto.output.ProductRecord;
import dev.attara.stockify.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @MutationMapping
    @Secured({})
    public OrderRecord createOrder(@Argument List<ProductLineDTO> productLines) {
        Long userId = 1L; // Get Current user Id
        return orderService.createOrder(new CreateOrderDTO(userId, productLines));
    }

    @MutationMapping
    @Secured({})
    public OrderRecord updateOrder(@Argument Long id, @Argument List<ProductLineDTO> productLines) {
        return orderService.updateOrder(id, productLines);
    }

    @MutationMapping
    public boolean deleteOrder(@Argument Long id) {
        return orderService.deleteOrder(id);
    }

    @QueryMapping
    public OrderRecord order(@Argument Long id) {
        return orderService.getOrderById(id);
    }

    @QueryMapping
    public List<OrderRecord> orders() {
        return orderService.allOrders();
    }

    @QueryMapping
    public List<OrderRecord> ordersByUser(@Argument Long userId) {
        return orderService.ordersByUserId(userId);
    }

    @QueryMapping
    public List<ProductLineRecord> productsInOrder(@Argument Long orderId) {
        return orderService.getOrderProducts(orderId);
    }
}
