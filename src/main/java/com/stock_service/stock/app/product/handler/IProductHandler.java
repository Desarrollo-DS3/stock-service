package com.stock_service.stock.app.product.handler;

import com.stock_service.stock.app.product.dto.ProductRequest;

public interface IProductHandler {
    void createProduct(ProductRequest productRequest);
}
