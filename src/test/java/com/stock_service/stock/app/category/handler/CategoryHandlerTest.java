package com.stock_service.stock.app.category.handler;

import com.stock_service.stock.app.category.dto.CategoryRequest;
import com.stock_service.stock.app.category.dto.CategoryResponse;
import com.stock_service.stock.app.category.mapper.ICategoryRequestMapper;
import com.stock_service.stock.app.category.mapper.ICategoryResponseMapper;
import com.stock_service.stock.domain.category.api.ICategoryServicePort;
import com.stock_service.stock.domain.category.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.stock_service.stock.utils.TestConstants.*;
import static org.mockito.Mockito.*;

class CategoryHandlerTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

    @Mock
    private ICategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
    }

    @Test
    void shouldCallCreateCategoryOnCategoryCreateService() {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(VALID_CATEGORY_NAME);
        categoryRequest.setDescription(VALID_CATEGORY_DESCRIPTION);

        when(categoryRequestMapper.toCategory(categoryRequest)).thenReturn(category);

        categoryHandler.createBrand(categoryRequest);

        verify(categoryServicePort, times(1)).createCategory(category);
    }

    @Test
    void shouldReturnCategoryListWheGetAllCategories() {
        CategoryResponse categoryResponse = new CategoryResponse(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);

        when(categoryResponseMapper.toCategoryResponseList(List.of(category))).thenReturn(List.of(categoryResponse));

        categoryHandler.getAllCategories();

        verify(categoryServicePort, times(1)).getAllCategories();
    }
}