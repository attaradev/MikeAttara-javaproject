package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dtos.ProductRecord;
import dev.attara.stockify.application.services.productmanagement.addproduct.AddProduct;
import dev.attara.stockify.application.services.productmanagement.addproduct.AddProductHandler;
import dev.attara.stockify.application.services.productmanagement.deleteproduct.DeleteProduct;
import dev.attara.stockify.application.services.productmanagement.deleteproduct.DeleteProductHandler;
import dev.attara.stockify.application.services.productmanagement.getallproducts.GetAllProducts;
import dev.attara.stockify.application.services.productmanagement.getallproducts.GetAllProductsHandler;
import dev.attara.stockify.application.services.productmanagement.getlowstockproducts.GetLowStockProducts;
import dev.attara.stockify.application.services.productmanagement.getlowstockproducts.GetLowStockProductsHandler;
import dev.attara.stockify.application.services.productmanagement.getproduct.GetProduct;
import dev.attara.stockify.application.services.productmanagement.getproduct.GetProductHandler;
import dev.attara.stockify.application.services.productmanagement.updateproduct.ProductUpdateData;
import dev.attara.stockify.application.services.productmanagement.updateproduct.UpdateProduct;
import dev.attara.stockify.application.services.productmanagement.updateproduct.UpdateProductHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Controller responsible for handling GraphQL queries and mutations related to products.
 * This controller manages operations such as adding, updating, deleting, and retrieving products and product-related data.
 */
@Slf4j
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


    /**
     * Creates a new product.
     *
     * @param productData The data for the new product.
     * @return The record of the created product.
     */
    @MutationMapping
    @Secured("ROLE_ADMIN")
    public ProductRecord createProduct(@Argument AddProduct productData) {
        try {
            log.info("Creating product: {}", productData);
            ProductRecord productRecord = addProductHandler.handle(productData);
            log.info("Product created successfully: {}", productRecord);
            return productRecord;
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            throw new RuntimeException("Failed to create product", e);
        }
    }

    /**
     * Updates an existing product.
     *
     * @param productId   The ID of the product to update.
     * @param productData The updated data for the product.
     * @return The record of the updated product.
     */
    @MutationMapping
    @Secured("ROLE_ADMIN")
    public ProductRecord updateProduct(@Argument @NonNull String productId, @Argument ProductUpdateData productData) {
        try {
            log.info("Updating product with ID {}: {}", productId, productData);
            ProductRecord productRecord = updateProductHandler.handle(new UpdateProduct(productId, productData));
            log.info("Product updated successfully: {}", productRecord);
            return productRecord;
        } catch (Exception e) {
            log.error("Error updating product: {}", e.getMessage());
            throw new RuntimeException("Failed to update product", e);
        }
    }

    /**
     * Deletes a product.
     *
     * @param productId The ID of the product to delete.
     * @return True if the product was successfully deleted, false otherwise.
     */
    @MutationMapping
    @Secured("ROLE_ADMIN")
    public boolean deleteProduct(@Argument @NonNull String productId) {
        try {
            log.info("Deleting product with ID: {}", productId);
            boolean deleted = deleteProductHandler.handle(new DeleteProduct(productId));
            if (deleted) {
                log.info("Product deleted successfully with ID: {}", productId);
            } else {
                log.warn("Product not found with ID: {}", productId);
            }
            return deleted;
        } catch (Exception e) {
            log.error("Error deleting product: {}", e.getMessage());
            throw new RuntimeException("Failed to delete product", e);
        }
    }

    /**
     * Retrieves all products.
     *
     * @return The list of all products.
     */
    @QueryMapping
    public List<ProductRecord> products() {
        try {
            log.info("Fetching all products");
            List<ProductRecord> products = getAllProductsHandler.handle(new GetAllProducts());
            log.info("Fetched {} products", products.size());
            return products;
        } catch (Exception e) {
            log.error("Error retrieving all products: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve all products", e);
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The record of the retrieved product.
     */
    @QueryMapping
    public ProductRecord product(@Argument @NonNull String productId) {
        try {
            log.info("Fetching product with ID: {}", productId);
            ProductRecord productRecord = getProductHandler.handle(new GetProduct(productId));
            log.info("Fetched product: {}", productRecord);
            return productRecord;
        } catch (Exception e) {
            log.error("Error retrieving product: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve product", e);
        }
    }

    /**
     * Retrieves products with low stock.
     *
     * @param threshold The threshold for defining low stock.
     * @return The list of products with low stock.
     */
    @QueryMapping
    @Secured("ROLE_ADMIN")
    List<ProductRecord> lowStockProducts(@Argument int threshold) {
        try {
            log.info("Fetching low stock products with threshold: {}", threshold);
            List<ProductRecord> lowStockProducts = getLowStockProductsHandler.handle(new GetLowStockProducts(threshold));
            log.info("Fetched {} low stock products", lowStockProducts.size());
            return lowStockProducts;
        } catch (Exception e) {
            log.error("Error retrieving low stock products: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve low stock products", e);
        }
    }
}
