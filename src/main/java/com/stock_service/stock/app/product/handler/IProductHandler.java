package com.stock_service.stock.app.product.handler;

import com.stock_service.stock.app.product.dto.ProductRequest;
import com.stock_service.stock.app.product.dto.ProductTransaction;

public interface IProductHandler {
    void createProduct(ProductRequest productRequest);

    void restoreStocks(ProductTransaction transaction);

    void discountStocks(ProductTransaction transaction);
}
