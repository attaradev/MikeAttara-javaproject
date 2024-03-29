package dev.attara.stockify.application.services.productmanagement.getproduct;

import dev.attara.stockify.application.dtos.ProductRecord;
import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.domain.models.Product;
import dev.attara.stockify.domain.repositories.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handler for retrieving a product by its ID.
 */
@Service
@RequiredArgsConstructor
public class GetProductHandler implements ServiceHandler<GetProduct, ProductRecord> {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    /**
     * Handles the request to retrieve a product by its ID.
     *
     * @param getProduct The request containing the product ID.
     * @return The product record associated with the given ID.
     */
    @Override
    public ProductRecord handle(GetProduct getProduct) {
        Product product = repository.findById(getProduct.id());
        return mapper.mapToRecord(product);
    }
}
