package dev.attara.stockify.application.service.productmanagement.getproduct;

import dev.attara.stockify.application.dto.ProductRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
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
