package com.stock_service.stock.domain.product.spi;

import com.stock_service.stock.domain.product.model.Product;

public interface IProductPersistencePort {
    void createProduct(Product product);
}
