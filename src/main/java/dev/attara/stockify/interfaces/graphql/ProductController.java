package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dto.input.CreateProductDTO;
import dev.attara.stockify.application.dto.input.UpdateProductDTO;
import dev.attara.stockify.application.dto.output.ProductRecord;
import dev.attara.stockify.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
public class ProductController {

    private final ProductService productService;

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public ProductRecord createProduct(@Argument CreateProductDTO product) {
        return productService.createProduct(product);
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public ProductRecord updateProduct(@Argument long id, @Argument UpdateProductDTO data) {
        return productService.updateProduct(id, data);
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public boolean deleteProduct(@Argument long id) {
        return productService.deleteProduct(id);
    }

    @QueryMapping
    public List<ProductRecord> products() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public ProductRecord product(@Argument long id) {
        return productService.getProductById(id);
    }

    @QueryMapping
    @Secured("ROLE_ADMIN")
    List<ProductRecord> lowStockProducts(@Argument int threshold) {
        return productService.getLowStockProducts(threshold);
    }

}
