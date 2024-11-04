package com.stock_service.stock.domain.category.spi;

import com.stock_service.stock.domain.category.model.Category;

public interface ICategoryPersistencePort {
    void createCategory(Category category);
    Boolean isCategoryPresentByName(String categoryName);
}
