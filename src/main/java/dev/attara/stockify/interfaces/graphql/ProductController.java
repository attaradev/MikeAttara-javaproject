package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dto.input.CreateProductDTO;
import dev.attara.stockify.application.dto.input.UpdateProductDTO;
import dev.attara.stockify.application.dto.output.ProductRecord;
import dev.attara.stockify.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @MutationMapping
    public ProductRecord createProduct(@Argument CreateProductDTO product) {
        return productService.createProduct(product);
    }

    @MutationMapping
    public ProductRecord updateProduct(@Argument Long id, @Argument UpdateProductDTO data) {
        return productService.updateProduct(id, data);
    }

    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }

    @QueryMapping
    public List<ProductRecord> products() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public ProductRecord product(@Argument Long id) {
        return productService.getProductById(id);
    }

    @QueryMapping
    List<ProductRecord> lowStockProducts(@Argument int threshold) {
        if (threshold <= 0) {
            threshold = 10;
        }
        return productService.getLowStockProducts(threshold);
    }
}
