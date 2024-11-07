package com.stock_service.stock.domain.product.spi;

import com.stock_service.stock.domain.product.model.Product;

import java.util.Optional;

public interface IProductPersistencePort {
    void createProduct(Product product);

    Optional<Product> getProductById(Long id);

    void updateProduct(Product product);
}
