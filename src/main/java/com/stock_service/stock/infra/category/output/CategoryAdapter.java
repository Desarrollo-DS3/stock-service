package com.stock_service.stock.infra.category.output;

import com.stock_service.stock.domain.category.model.Category;
import com.stock_service.stock.domain.category.spi.ICategoryPersistencePort;

import java.util.List;
import java.util.Optional;

public class CategoryAdapter implements ICategoryPersistencePort {

    ICategoryRepository categoryRepository;
    ICategoryMapper categoryMapper;

    public CategoryAdapter(ICategoryRepository categoryRepository, ICategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(categoryMapper.toEntity(category));
    }

    @Override
    public Boolean isCategoryPresentByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).map(categoryMapper::toCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.toCategoriesList(categoryRepository.findAll());
    }
}
