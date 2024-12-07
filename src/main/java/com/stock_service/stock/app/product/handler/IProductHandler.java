package com.stock_service.stock.app.product.handler;

import com.stock_service.stock.app.product.dto.ProductRequest;
import com.stock_service.stock.app.product.dto.ProductResponse;
import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.app.product.dto.SupplyRequest;
import com.stock_service.stock.domain.product.model.Supply;
import jakarta.validation.Valid;

import java.util.List;

public interface IProductHandler {
    void createProduct(ProductRequest productRequest);

    void restoreStocks(ProductTransaction transaction);

    void discountStocks(ProductTransaction transaction);

    void supplyProduct(SupplyRequest supplyRequest);

    void restoreStock(SupplyRequest supplyRequest);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(long id);
}
