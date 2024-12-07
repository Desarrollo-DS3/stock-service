package com.stock_service.stock.app.category.handler;

import com.stock_service.stock.app.category.dto.CategoryRequest;
import com.stock_service.stock.app.category.dto.CategoryResponse;
import com.stock_service.stock.app.category.mapper.ICategoryRequestMapper;
import com.stock_service.stock.app.category.mapper.ICategoryResponseMapper;
import com.stock_service.stock.domain.category.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryHandler implements ICategoryHandler {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Override
    public void createBrand(CategoryRequest categoryRequest) {
        categoryServicePort.createCategory(categoryRequestMapper.toCategory(categoryRequest));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryResponseMapper.toCategoryResponseList(categoryServicePort.getAllCategories());
    }
}
