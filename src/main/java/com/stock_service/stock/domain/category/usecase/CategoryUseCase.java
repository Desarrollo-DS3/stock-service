package com.stock_service.stock.domain.category.usecase;

import com.stock_service.stock.domain.brand.exception.ex.BrandNotFoundByIdException;
import com.stock_service.stock.domain.category.api.ICategoryServicePort;
import com.stock_service.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.stock_service.stock.domain.category.exception.ex.CategoryNotValidFieldException;
import com.stock_service.stock.domain.category.model.Category;
import com.stock_service.stock.domain.category.spi.ICategoryPersistencePort;

import java.util.List;

import static com.stock_service.stock.domain.category.exception.CategoryExceptionMessage.*;
import static com.stock_service.stock.domain.category.util.CategoryConstants.MAX_DESCRIPTION_LENGTH;
import static com.stock_service.stock.domain.category.util.CategoryConstants.MAX_NAME_LENGTH;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createCategory(Category category) {

        String categoryName = category.getName();
        String categoryDescription = category.getDescription();

        if (categoryName == null || categoryName.trim().isEmpty())
            throw new CategoryNotValidFieldException(EMPTY_NAME);

        if (categoryDescription == null || categoryDescription.trim().isEmpty())
            throw new CategoryNotValidFieldException(EMPTY_DESCRIPTION);

        if (categoryName.length() > MAX_NAME_LENGTH)
            throw new CategoryNotValidFieldException(TOO_LONG_NAME);

        if (categoryDescription.length() > MAX_DESCRIPTION_LENGTH)
            throw new CategoryNotValidFieldException(TOO_LONG_DESCRIPTION);

        if (Boolean.TRUE.equals(categoryPersistencePort.isCategoryPresentByName(categoryName)))
            throw new CategoryAlreadyExistException(ALREADY_EXIST_CATEGORY);

        categoryPersistencePort.createCategory(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryPersistencePort.getCategoryById(id)
                .orElseThrow(() -> new BrandNotFoundByIdException(NO_FOUND_CATEGORY));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryPersistencePort.getAllCategories();
    }
}
