package com.stock_service.stock.app.category.handler;

import com.stock_service.stock.app.category.dto.CategoryRequest;
import com.stock_service.stock.app.category.dto.CategoryResponse;

import java.util.List;

public interface ICategoryHandler {
    void createBrand(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();
}
