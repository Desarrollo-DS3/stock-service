package com.stock_service.stock.domain.product.api;

import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.domain.product.model.Product;
import com.stock_service.stock.domain.product.model.Supply;

public interface IProductServicePort {
    void createProduct(Product product);

    void updateStock(Long productId, int amount);

    void restoreStocks(ProductTransaction transaction);

    void discountStocks(ProductTransaction transaction);

    void supplyProduct(Supply supply);

    void restoreStock(Supply supply);
}
