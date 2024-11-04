package com.stock_service.stock.app.category.handler;

import com.stock_service.stock.app.category.dto.CategoryRequest;
import com.stock_service.stock.app.category.mapper.ICategoryRequestMapper;
import com.stock_service.stock.domain.category.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryHandler implements ICategoryHandler {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    @Override
    public void createBrand(CategoryRequest categoryRequest) {
        categoryServicePort.createCategory(categoryRequestMapper.toCategory(categoryRequest));
    }
}
