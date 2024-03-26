package dev.attara.stockify.application.service.productmanagement.getallproducts;

import dev.attara.stockify.application.dto.ProductRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handler for retrieving all products.
 */
@Service
@RequiredArgsConstructor
public class GetAllProductsHandler implements ServiceHandler<GetAllProducts, List<ProductRecord>> {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    /**
     * Handles the request to retrieve all products.
     *
     * @param getAllProducts The request to retrieve all products.
     * @return A list of product records containing all products.
     */
    @Override
    public List<ProductRecord> handle(GetAllProducts getAllProducts) {
        return repository.findAll().stream()
                .map(mapper::mapToRecord)
                .toList();
    }

}
