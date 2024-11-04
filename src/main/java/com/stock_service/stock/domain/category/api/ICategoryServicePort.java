package com.stock_service.stock.domain.category.api;

import com.stock_service.stock.domain.category.model.Category;

public interface ICategoryServicePort {
    void createCategory(Category category);
}
