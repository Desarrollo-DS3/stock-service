package com.stock_service.stock.domain.category.usecase;

import com.stock_service.stock.domain.category.exception.CategoryExceptionMessage;
import com.stock_service.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.stock_service.stock.domain.category.exception.ex.CategoryNotValidFieldException;
import com.stock_service.stock.domain.category.model.Category;
import com.stock_service.stock.domain.category.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.stock_service.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaxLength() {
        category.setName(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m"
        );

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.TOO_LONG_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        category.setDescription(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. " +
                        "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur rid"
        );

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        category.setName(null);

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        category.setName("");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        category.setDescription(null);

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        category.setDescription("");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoryAlreadyExists() {
        when(categoryPersistencePort.isCategoryPresentByName(VALID_CATEGORY_NAME)).thenReturn(true);

        CategoryAlreadyExistException exception = assertThrows(
                CategoryAlreadyExistException.class, () -> categoryUseCase.createCategory(category)
        );

        verify(categoryPersistencePort, times(1)).isCategoryPresentByName(VALID_CATEGORY_NAME);
        assertEquals(CategoryExceptionMessage.ALREADY_EXIST_CATEGORY, exception.getMessage());
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        assertDoesNotThrow(() -> categoryUseCase.createCategory(category));

        verify(categoryPersistencePort).createCategory(category);
    }

    @Test
    void shouldReturnAllCategories() {
        Category category1 = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        Category category2 = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        List<Category> categories = List.of(category1, category2);

        when(categoryPersistencePort.getAllCategories()).thenReturn(categories);

        assertNotNull(categoryUseCase.getAllCategories());
        assertEquals(2, categoryUseCase.getAllCategories().size());
        assertTrue(categoryUseCase.getAllCategories().containsAll(categories));
    }
}