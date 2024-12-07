package com.stock_service.stock.app.category.mapper;

import com.stock_service.stock.app.category.dto.CategoryResponse;
import com.stock_service.stock.domain.category.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categoryList);
}
