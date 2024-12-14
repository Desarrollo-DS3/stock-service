package com.stock_service.stock.infra.category.config;

import com.stock_service.stock.domain.category.api.ICategoryServicePort;
import com.stock_service.stock.domain.category.spi.ICategoryPersistencePort;
import com.stock_service.stock.domain.category.usecase.CategoryUseCase;
import com.stock_service.stock.infra.category.output.CategoryAdapter;
import com.stock_service.stock.infra.category.output.ICategoryMapper;
import com.stock_service.stock.infra.category.output.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanCategoryConfig {
    private final ICategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistence() {
        return new CategoryAdapter(categoryRepository, categoryMapper);
    }

    @Bean
    public ICategoryServicePort categoryCreateService() {
        return new CategoryUseCase(categoryPersistence());
    }
}
