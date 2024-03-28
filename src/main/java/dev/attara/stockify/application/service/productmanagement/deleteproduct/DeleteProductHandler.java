package dev.attara.stockify.application.service.productmanagement.deleteproduct;

import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.domain.repository.ProductRepository;
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
