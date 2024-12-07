package com.stock_service.stock.app.product.mapper;

import com.stock_service.stock.app.product.dto.ProductResponse;
import com.stock_service.stock.domain.product.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductResponseMapper {
    ProductResponse toResponse(Product product);
    List<ProductResponse> toResponses(List<Product> products);
}
