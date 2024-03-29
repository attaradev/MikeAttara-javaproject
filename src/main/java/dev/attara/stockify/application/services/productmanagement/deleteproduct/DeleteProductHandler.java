package dev.attara.stockify.application.services.productmanagement.deleteproduct;

import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.models.Product;
import dev.attara.stockify.domain.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handler for deleting a product.
 */
@Service
@RequiredArgsConstructor
public class DeleteProductHandler implements ServiceHandler<DeleteProduct, Boolean> {

    private final ProductRepository repository;

    /**
     * Handles the request to delete a product.
     *
     * @param deleteProduct The request to delete a product.
     * @return True if the product is successfully deleted, false otherwise.
     */
    @Override
    public Boolean handle(DeleteProduct deleteProduct) {
        Product product = repository.findById(deleteProduct.id());
        repository.delete(product);
        return true;
    }
}
