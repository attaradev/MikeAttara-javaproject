package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dto.ProductRecord;
import dev.attara.stockify.application.service.productmanagement.addproduct.AddProduct;
import dev.attara.stockify.application.service.productmanagement.addproduct.AddProductHandler;
import dev.attara.stockify.application.service.productmanagement.deleteproduct.DeleteProduct;
import dev.attara.stockify.application.service.productmanagement.deleteproduct.DeleteProductHandler;
import dev.attara.stockify.application.service.productmanagement.getallproducts.GetAllProducts;
import dev.attara.stockify.application.service.productmanagement.getallproducts.GetAllProductsHandler;
import dev.attara.stockify.application.service.productmanagement.getlowstockproducts.GetLowStockProducts;
import dev.attara.stockify.application.service.productmanagement.getlowstockproducts.GetLowStockProductsHandler;
import dev.attara.stockify.application.service.productmanagement.getproduct.GetProduct;
import dev.attara.stockify.application.service.productmanagement.getproduct.GetProductHandler;
import dev.attara.stockify.application.service.productmanagement.updateproduct.ProductUpdateData;
import dev.attara.stockify.application.service.productmanagement.updateproduct.UpdateProduct;
import dev.attara.stockify.application.service.productmanagement.updateproduct.UpdateProductHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final AddProductHandler addProductHandler;

    private final UpdateProductHandler updateProductHandler;

    private final DeleteProductHandler deleteProductHandler;

    private final GetAllProductsHandler getAllProductsHandler;

    private final GetProductHandler getProductHandler;

    private final GetLowStockProductsHandler getLowStockProductsHandler;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public ProductRecord createProduct(@Argument AddProduct productData) {
        logger.info("Creating product: {}", productData);
        ProductRecord productRecord = addProductHandler.handle(productData);
        logger.info("Product created successfully: {}", productRecord);
        return productRecord;
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public ProductRecord updateProduct(@Argument long productId, @Argument ProductUpdateData productData) {
        logger.info("Updating product with ID {}: {}", productId, productData);
        ProductRecord productRecord = updateProductHandler.handle(new UpdateProduct(productId, productData));
        logger.info("Product updated successfully: {}", productRecord);
        return productRecord;
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public boolean deleteProduct(@Argument long productId) {
        logger.info("Deleting product with ID: {}", productId);
        boolean deleted = deleteProductHandler.handle(new DeleteProduct(productId));
        if (deleted) {
            logger.info("Product deleted successfully with ID: {}", productId);
        } else {
            logger.warn("Product not found with ID: {}", productId);
        }
        return deleted;
    }

    @QueryMapping
    public List<ProductRecord> products() {
        logger.info("Fetching all products");
        List<ProductRecord> products = getAllProductsHandler.handle(new GetAllProducts());
        logger.info("Fetched {} products", products.size());
        return products;
    }

    @QueryMapping
    public ProductRecord product(@Argument long productId) {
        logger.info("Fetching product with ID: {}", productId);
        ProductRecord productRecord = getProductHandler.handle(new GetProduct(productId));
        logger.info("Fetched product: {}", productRecord);
        return productRecord;
    }

    @QueryMapping
    @Secured("ROLE_ADMIN")
    List<ProductRecord> lowStockProducts(@Argument int threshold) {
        logger.info("Fetching low stock products with threshold: {}", threshold);
        List<ProductRecord> lowStockProducts = getLowStockProductsHandler.handle(new GetLowStockProducts(threshold));
        logger.info("Fetched {} low stock products", lowStockProducts.size());
        return lowStockProducts;
    }
}
