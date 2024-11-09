package com.stock_service.stock.app.product.mapper;

import com.stock_service.stock.app.product.dto.ProductRequest;
import com.stock_service.stock.app.product.dto.SupplyRequest;
import com.stock_service.stock.domain.product.model.Product;
import com.stock_service.stock.domain.product.model.Supply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.stock_service.stock.domain.product.util.ProductConstants.*;

@Mapper(componentModel = "spring")
public interface IProductRequestMapper {
    @Mapping(target = PRODUCT_ID, ignore = true)
    @Mapping(target = CATEGORY, ignore = true)
    @Mapping(target = BRAND, ignore = true)
    Product toProduct(ProductRequest productRequest);
    @Mapping(target = "userId", ignore = true)
    Supply toSupply(SupplyRequest supplyRequest);
}
