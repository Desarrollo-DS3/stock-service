package com.stock_service.stock.app.category.mapper;

import com.stock_service.stock.app.category.dto.CategoryRequest;
import com.stock_service.stock.domain.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {
    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryRequest categoryRequest);
}
