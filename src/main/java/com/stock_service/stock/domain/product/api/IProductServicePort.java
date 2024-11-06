package com.stock_service.stock.domain.product.api;

import com.stock_service.stock.domain.product.model.Product;

public interface IProductServicePort {
    void createProduct(Product product);
}
