package com.stock_service.stock.domain.brand.usecase;

import com.stock_service.stock.domain.brand.exception.BrandExceptionMessage;
import com.stock_service.stock.domain.brand.exception.ex.BrandAlreadyExistException;
import com.stock_service.stock.domain.brand.exception.ex.BrandNotFoundByIdException;
import com.stock_service.stock.domain.brand.exception.ex.BrandNotValidFieldException;
import com.stock_service.stock.domain.brand.model.Brand;
import com.stock_service.stock.domain.brand.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.stock_service.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    private Brand brand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        brand = new Brand(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaxLength() {
        brand.setName(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m"
        );


        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.TOO_LONG_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        brand.setDescription(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                        "Aenean commodo ligula eget dolor. Aenean massa. " +
                        "Cum sociis natoque penatibus et magnis dis parturient montes, " +
                        "nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu"
        );

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        brand.setName(null);

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        brand.setName("");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        brand.setDescription(null);

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        brand.setDescription("");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBrandAlreadyExists() {
        when(brandPersistencePort.isBrandPresentByName(VALID_BRAND_NAME)).thenReturn(true);

        BrandAlreadyExistException exception = assertThrows(
                BrandAlreadyExistException.class, () -> brandUseCase.createBrand(brand)
        );

        verify(brandPersistencePort, times(1)).isBrandPresentByName(VALID_BRAND_NAME);
        assertEquals(BrandExceptionMessage.ALREADY_EXIST_BRAND, exception.getMessage());
    }

    @Test
    void shouldCreateBrandSuccessfully() {
        assertDoesNotThrow(() -> brandUseCase.createBrand(brand));

        verify(brandPersistencePort).createBrand(brand);
    }

    @Test
    void getBrandById_ShouldReturnBrand_WhenBrandExists() {
        when(brandPersistencePort.getBrandById(VALID_BRAND_ID)).thenReturn(Optional.of(brand));

        Brand actualBrand = brandUseCase.getBrandById(VALID_BRAND_ID);

        assertEquals(brand, actualBrand);
    }

    @Test
    void getBrandByIdShouldThrowBrandNotFoundByIdExceptionWhenBrandDoesNotExist() {
        when(brandPersistencePort.getBrandById(VALID_BRAND_ID)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundByIdException.class, () -> brandUseCase.getBrandById(VALID_BRAND_ID));
    }

    @Test
    void shouldReturnAllBrands() {
        Brand brand1 = new Brand(1L, "Brand One", "Description of Brand One");
        Brand brand2 = new Brand(2L, "Brand Two", "Description of Brand Two");
        List<Brand> expectedBrands = List.of(brand1, brand2);

        when(brandPersistencePort.getAllBrands()).thenReturn(expectedBrands);

        List<Brand> actualBrands = brandUseCase.getAllBrands();

        assertNotNull(actualBrands);
        assertEquals(2, actualBrands.size());
        assertTrue(actualBrands.containsAll(expectedBrands));
    }

}