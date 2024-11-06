package com.stock_service.stock.domain.category.spi;

import com.stock_service.stock.domain.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryPersistencePort {
    void createCategory(Category category);
    Boolean isCategoryPresentByName(String categoryName);
    Optional<Category> getCategoryById(Long id);
}
