package com.stock_service.stock.domain.category.api;

import com.stock_service.stock.domain.category.model.Category;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public interface ICategoryServicePort {
    void createCategory(Category category);
    Category getCategoryById(Long id);
}
