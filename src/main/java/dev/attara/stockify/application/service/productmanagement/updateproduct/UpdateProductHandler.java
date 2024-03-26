package dev.attara.stockify.application.service.productmanagement.updateproduct;

import dev.attara.stockify.application.dto.ProductRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handler for updating product information.
 */
@Service
@RequiredArgsConstructor
public class UpdateProductHandler implements ServiceHandler<UpdateProduct, ProductRecord> {

    private final ProductRepository repository;
    private final UpdateProductValidator validator;
    private final ProductMapper mapper;

    /**
     * Handles the request to update product information.
     *
     * @param updateProduct The request containing the product ID and update data.
     * @return The updated product record.
     */
    @Override
    public ProductRecord handle(UpdateProduct updateProduct) {
        validator.validate(updateProduct);
        Product product = repository.findById(updateProduct.id());
        ProductUpdateData updateData = updateProduct.updateData();
        if (updateData.getName() != null) {
            product.setName(updateData.getName());
        }
        if (updateData.getPrice() != Float.NEGATIVE_INFINITY) {
            product.setPrice(updateData.getPrice());
        }
        if (updateData.getStock() != Integer.MIN_VALUE) {
            product.setStock(updateData.getStock());
        }
        repository.save(product);
        return mapper.mapToRecord(product);
    }
}
