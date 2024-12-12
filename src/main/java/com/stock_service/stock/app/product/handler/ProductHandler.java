package com.stock_service.stock.app.product.handler;

import com.stock_service.stock.app.product.dto.ProductRequest;
import com.stock_service.stock.app.product.dto.ProductResponse;
import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.app.product.dto.SupplyRequest;
import com.stock_service.stock.app.product.mapper.IProductRequestMapper;
import com.stock_service.stock.app.product.mapper.IProductResponseMapper;
import com.stock_service.stock.domain.brand.api.IBrandServicePort;
import com.stock_service.stock.domain.brand.model.Brand;
import com.stock_service.stock.domain.category.api.ICategoryServicePort;
import com.stock_service.stock.domain.category.model.Category;
import com.stock_service.stock.domain.product.api.IProductServicePort;
import com.stock_service.stock.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;
    private final IBrandServicePort brandServicePort;
    private final ICategoryServicePort categoryServicePort;

    @Override
    public void createProduct(ProductRequest productRequest) {
        Brand brand = brandServicePort.getBrandById(productRequest.getBrandId());
        Category category = categoryServicePort.getCategoryById(productRequest.getCategoryId());

        Product product = productRequestMapper.toProduct(productRequest);

        product.setBrand(brand);
        product.setCategory(category);

        productServicePort.createProduct(product);
    }

    @Override
    public void restoreStocks(ProductTransaction transaction) {
        productServicePort.restoreStocks(transaction);
    }

    @Override
    public void discountStocks(ProductTransaction transaction) {
        productServicePort.discountStocks(transaction);
    }

    @Override
    public void supplyProduct(SupplyRequest supplyRequest) {
        productServicePort.supplyProduct(productRequestMapper.toSupply(supplyRequest));
    }

    @Override
    public void restoreStock(SupplyRequest supplyRequest) {
        productServicePort.restoreStock(productRequestMapper.toSupply(supplyRequest));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productResponseMapper.toResponses(productServicePort.getAllProducts());
    }

    @Override
    public ProductResponse getProductById(long id) {
        return productResponseMapper.toResponse(productServicePort.getProductById(id));
    }

    @Override
    public void buyProduct(SupplyRequest supplyRequest) {
        productServicePort.buyProduct(productRequestMapper.toSupply(supplyRequest));
    }
}
