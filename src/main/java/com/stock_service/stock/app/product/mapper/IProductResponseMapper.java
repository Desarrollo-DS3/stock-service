package com.stock_service.stock.app.product.mapper;

import com.stock_service.stock.app.product.dto.ProductResponse;
import com.stock_service.stock.domain.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductResponseMapper {
    ProductResponse toResponse(Product product);
}
