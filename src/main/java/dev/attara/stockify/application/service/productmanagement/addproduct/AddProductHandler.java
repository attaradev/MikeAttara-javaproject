package dev.attara.stockify.application.service.productmanagement.addproduct;

import dev.attara.stockify.application.dto.ProductRecord;
import dev.attara.stockify.application.service.ServiceHandler;
import dev.attara.stockify.domain.model.Product;
import dev.attara.stockify.domain.repository.ProductRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handler for adding a new product.
 */
@Service
@RequiredArgsConstructor
public class AddProductHandler implements ServiceHandler<AddProduct, ProductRecord> {

    private final AddProductValidator validator;
    private final ProductRepository repository;
    private final ProductMapper mapper;

    /**
     * Handles the request to add a new product.
     *
     * @param addProduct The request containing the details of the new product.
     * @return The record of the added product.
     */
    @Override
    public ProductRecord handle(AddProduct addProduct) {
        validator.validate(addProduct);
        long id = repository.nextId();
        String name = addProduct.name();
        double price = addProduct.price();
        int stock = addProduct.stock();
        Product product = Product.create(id, name, price, stock);
        repository.save(product);
        return mapper.mapToRecord(product);
    }
}
