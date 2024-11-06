package com.stock_service.stock.app.product.mapper;

import com.stock_service.stock.app.product.dto.ProductRequest;
import com.stock_service.stock.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.stock_service.stock.domain.product.util.ProductConstants.*;

@Mapper(componentModel = "spring")
public interface IProductRequestMapper {
    @Mapping(target = PRODUCT_ID, ignore = true)
    @Mapping(target = CATEGORY, ignore = true)
    @Mapping(target = BRAND, ignore = true)
    Product toProduct(ProductRequest productRequest);
}
