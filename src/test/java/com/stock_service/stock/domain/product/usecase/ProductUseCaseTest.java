package com.stock_service.stock.domain.product.usecase;

import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.app.product.dto.ProductTransactionItem;
import com.stock_service.stock.domain.brand.model.Brand;
import com.stock_service.stock.domain.category.model.Category;
import com.stock_service.stock.domain.product.exception.ProductExceptionMessage;
import com.stock_service.stock.domain.product.exception.ex.*;
import com.stock_service.stock.domain.product.model.Product;
import com.stock_service.stock.domain.product.model.Supply;
import com.stock_service.stock.domain.product.spi.IProductNotifyPort;
import com.stock_service.stock.domain.product.spi.IProductPersistencePort;
import com.stock_service.stock.infra.exception.ex.NotificationException;
import com.stock_service.stock.infra.exception.ex.RollbackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.stock_service.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private IProductNotifyPort productNotifyPort;

    @InjectMocks
    private ProductUseCase productUseCase;


    private Product product;
    private Supply supply;
    private ProductTransaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Category category = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        Brand brand = new Brand(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        product = new Product(
                VALID_PRODUCT_ID,
                VALID_PRODUCT_NAME,
                VALID_PRODUCT_DESCRIPTION,
                VALID_PRODUCT_PRICE,
                VALID_PRODUCT_STOCK,
                brand,
                category);
        supply = new Supply(1L, 1L, 5);
        ProductTransactionItem item = new ProductTransactionItem(1L, 5, BigDecimal.TEN);
        transaction = new ProductTransaction(1L, List.of(item));
    }

    @Test
    void shouldThrowExceptionWhenBrandIsNull() {
        product.setBrand(null);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.EMPTY_BRAND_ID, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", ""})
    void shouldThrowExceptionWhenProductNameIsEmpty(String name) {
        String testName = name.equals("null") ? null : name;

        product.setName(testName);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "-1"})
    void shouldThrowExceptionWhenStockIsInvalid(String stock) {
        Integer testStock = stock.equals("null") ? null : Integer.parseInt(stock);
        String exceptionMessage = stock.equals("null")
                ? ProductExceptionMessage.EMPTY_STOCK
                : ProductExceptionMessage.NEGATIVE_STOCK;

        product.setStock(testStock);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "-1"})
    void shouldThrowExceptionWhenPriceIsInvalid(String price) {
        BigDecimal testPrice = price.equals("null") ? null : new BigDecimal(price);
        String exceptionMessage = price.equals("null")
                ? ProductExceptionMessage.EMPTY_PRICE
                : ProductExceptionMessage.NEGATIVE_PRICE;

        product.setPrice(testPrice);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void shouldCreateProductSuccessfully() {
        productUseCase.createProduct(product);

        verify(productPersistencePort, times(1)).createProduct(product);
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        Long productId = null;

        StockNotValidFieldException exception = assertThrows(
                StockNotValidFieldException.class, () -> productUseCase.updateStock(productId, VALID_PRODUCT_STOCK)
        );

        assertEquals(ProductExceptionMessage.EMPTY_PRODUCT, exception.getMessage());
        verify(productPersistencePort, never()).getProductById(anyLong());
        verify(productPersistencePort, never()).updateProduct(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Long productId = 9999L;

        when(productPersistencePort.getProductById(productId)).thenReturn(Optional.empty());

        ProductNotFoundByIdException exception = assertThrows(
                ProductNotFoundByIdException.class, () -> productUseCase.updateStock(productId, VALID_PRODUCT_STOCK)
        );

        assertEquals(ProductExceptionMessage.NO_FOUND_PRODUCT, exception.getMessage());
        verify(productPersistencePort,times(1)).getProductById(productId);
        verify(productPersistencePort, never()).updateProduct(any(Product.class));
    }

    @Test
    void shouldUpdateStockProductSuccessfully() {
        when(productPersistencePort.getProductById(VALID_PRODUCT_ID)).thenReturn(Optional.ofNullable(product));

        productUseCase.updateStock(VALID_PRODUCT_ID, VALID_PRODUCT_STOCK);

        verify(productPersistencePort, times(1)).getProductById(VALID_PRODUCT_ID);
        verify(productPersistencePort, times(1)).updateProduct(product);
    }

    @Test
    void shouldRestoreStocksSuccessfully() {
        when(productPersistencePort.getProductById(1L)).thenReturn(Optional.of(product));

        productUseCase.restoreStocks(transaction);

        verify(productPersistencePort, times(1)).updateProduct(product);
        verify(productNotifyPort, times(1)).notifyRollback(transaction);
    }

    @Test
    void shouldThrowExceptionWhenDiscountingStockWithInsufficientQuantity() {
        product.setStock(2);
        when(productPersistencePort.getProductById(1L)).thenReturn(Optional.of(product));

        InsufficientStockException exception = assertThrows(
                InsufficientStockException.class, () -> productUseCase.discountStocks(transaction)
        );

        assertEquals("Insufficient stock for product ID: 1", exception.getMessage());
        verify(productNotifyPort, times(1)).notifyRollback(transaction);
    }

    @Test
    void shouldDiscountStocksSuccessfully() {
        when(productPersistencePort.getProductById(1L)).thenReturn(Optional.of(product));

        productUseCase.discountStocks(transaction);

        assertEquals(0, product.getStock());
        verify(productPersistencePort, times(1)).updateProduct(product);
        verify(productNotifyPort, times(1)).notifySale(transaction);
    }

    @Test
    void shouldSupplyProductSuccessfully() {
        when(productPersistencePort.getProductById(1L)).thenReturn(Optional.of(product));

        productUseCase.supplyProduct(supply);

        assertEquals(10, product.getStock());
        verify(productPersistencePort, times(1)).updateProduct(product);
        verify(productNotifyPort, times(1)).notifySupply(supply);
    }

    @Test
    void shouldHandleSupplyNotificationFailure() {
        when(productPersistencePort.getProductById(1L)).thenReturn(Optional.of(product));
        doThrow(new RuntimeException("Notification error")).when(productNotifyPort).notifySupply(supply);

        NotificationException exception = assertThrows(
                NotificationException.class, () -> productUseCase.supplyProduct(supply)
        );

        assertEquals("Failed to send supply notification for product ID: 1. Stock has been reverted. Reason: Notification error", exception.getMessage());
        assertEquals(5, product.getStock());
        verify(productPersistencePort, times(2)).updateProduct(product); // Revert update
    }

    @Test
    void shouldRestoreStockSuccessfully() {
        when(productPersistencePort.getProductById(1L)).thenReturn(Optional.of(product));

        RollbackException exception = assertThrows(
                RollbackException.class, () -> productUseCase.restoreStock(supply)
        );

        assertEquals("Rollback triggered: stock update for product ID 1 reverted due to insufficient stock or invalid supply quantity: 5", exception.getMessage());
        assertEquals(0, product.getStock());
        verify(productPersistencePort, times(1)).updateProduct(product);
    }

    @Test
    void shouldGetAllProducts() {
        when(productPersistencePort.getAllProducts()).thenReturn(List.of(product));

        List<Product> products = productUseCase.getAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productPersistencePort, times(1)).getAllProducts();
    }

    @Test
    void shouldGetProductByIdSuccessfully() {
        when(productPersistencePort.getProductById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productUseCase.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals(product, foundProduct);
        verify(productPersistencePort, times(1)).getProductById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductByIdNotFound() {
        when(productPersistencePort.getProductById(2L)).thenReturn(Optional.empty());

        ProductNotFoundByIdException exception = assertThrows(
                ProductNotFoundByIdException.class, () -> productUseCase.getProductById(2L)
        );

        assertEquals("Product does not found", exception.getMessage());
        verify(productPersistencePort, times(1)).getProductById(2L);
    }
}