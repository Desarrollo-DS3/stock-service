package com.stock_service.stock.app.product.handler;

import com.stock_service.stock.app.product.dto.ProductRequest;
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
}
