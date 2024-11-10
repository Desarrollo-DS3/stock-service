package com.stock_service.stock.infra.product.output.persistence;

import com.stock_service.stock.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.stock_service.stock.domain.product.util.ProductConstants.BRAND;
import static com.stock_service.stock.domain.product.util.ProductConstants.CATEGORY;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    @Mapping(source = BRAND, target = BRAND)
    @Mapping(source = CATEGORY, target = CATEGORY)
    ProductEntity toEntity(Product product);

    Product toProduct(ProductEntity productEntity);
}
