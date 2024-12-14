package com.stock_service.stock.domain.category.api;

import com.stock_service.stock.domain.category.model.Category;

import java.util.List;

public interface ICategoryServicePort {
    void createCategory(Category category);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();
}
