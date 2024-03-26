package dev.attara.stockify.application.service.productmanagement.getlowstockproducts;

import dev.attara.stockify.application.dto.ProductRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handler for retrieving products with stock levels below a specified threshold.
 */
@Service
@RequiredArgsConstructor
public class GetLowStockProductsHandler implements ServiceHandler<GetLowStockProducts, List<ProductRecord>> {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    /**
     * Handles the request to retrieve products with stock levels below a specified threshold.
     *
     * @param getLowStockProducts The request containing the threshold for low stock.
     * @return A list of product records with stock levels below the specified threshold.
     */
    @Override
    public List<ProductRecord> handle(GetLowStockProducts getLowStockProducts) {
        int threshold = getLowStockProducts.threshold();
        return repository.findLowStockProducts(threshold + 1).stream()
                .map(mapper::mapToRecord)
                .toList();
    }

}
