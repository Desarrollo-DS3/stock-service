package com.stock_service.stock.app.product.handler;

import com.stock_service.stock.app.brand.dto.BrandResponse;
import com.stock_service.stock.app.category.dto.CategoryResponse;
import com.stock_service.stock.app.product.dto.ProductRequest;
import com.stock_service.stock.app.product.dto.ProductResponse;
import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.app.product.dto.SupplyRequest;
import com.stock_service.stock.app.product.mapper.IProductRequestMapper;
import com.stock_service.stock.app.product.mapper.IProductResponseMapper;
import com.stock_service.stock.domain.brand.api.IBrandServicePort;
import com.stock_service.stock.domain.brand.model.Brand;
import com.stock_service.stock.domain.category.api.ICategoryServicePort;
import com.stock_service.stock.domain.category.model.Category;
import com.stock_service.stock.domain.product.api.IProductServicePort;
import com.stock_service.stock.domain.product.model.Product;
import com.stock_service.stock.domain.product.model.Supply;
import com.stock_service.stock.domain.product.util.ProductConstants;
import com.stock_service.stock.domain.util.GlobalConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.stock_service.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductHandlerTest {

    @Mock
    private IProductServicePort productServicePort;

    @Mock
    private IProductRequestMapper productRequestMapper;

    @Mock
    private IProductResponseMapper productResponseMapper;

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private ICategoryServicePort categoryServicePort;

    @InjectMocks
    private ProductHandler productHandler;

    private Category category;
    private Brand brand;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        brand = new Brand(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        product = new Product(
                VALID_PRODUCT_ID,
                VALID_PRODUCT_NAME,
                VALID_PRODUCT_DESCRIPTION,
                VALID_PRODUCT_PRICE,
                VALID_PRODUCT_STOCK,
                brand,
                category);
    }

    @Test
    void shouldCallCreateProductOnProductCreateService(){
        ProductRequest productRequest = new ProductRequest();
        productRequest.setBrandId(VALID_BRAND_ID);
        productRequest.setCategoryId(VALID_CATEGORY_ID);

        when(brandServicePort.getBrandById(VALID_BRAND_ID)).thenReturn(brand);
        when(categoryServicePort.getCategoryById(VALID_CATEGORY_ID)).thenReturn(category);
        when(productRequestMapper.toProduct(productRequest)).thenReturn(product);

        productHandler.createProduct(productRequest);

        verify(brandServicePort, times(1)).getBrandById(VALID_BRAND_ID);
        verify(categoryServicePort, times(1)).getCategoryById(VALID_CATEGORY_ID);
        verify(productRequestMapper, times(1)).toProduct(productRequest);
        verify(productServicePort, times(1)).createProduct(product);
    }

    @Test
    void shouldReturnProductListWheGetAllProducts(){
        BrandResponse brandResponse = new BrandResponse(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        CategoryResponse categoryResponse = new CategoryResponse(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        ProductResponse productResponse = new ProductResponse(VALID_PRODUCT_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_PRODUCT_PRICE, VALID_PRODUCT_STOCK, brandResponse, categoryResponse);

        when(productResponseMapper.toResponses(List.of(product))).thenReturn(List.of(productResponse));

        productHandler.getAllProducts();

        verify(productServicePort, times(1)).getAllProducts();
    }

    @Test
    void shouldCallRestoreStocksOnProductServicePort() {
        ProductTransaction transaction = mock(ProductTransaction.class);

        productHandler.restoreStocks(transaction);

        verify(productServicePort, times(1)).restoreStocks(transaction);
    }

    @Test
    void shouldCallDiscountStocksOnProductServicePort() {
        ProductTransaction transaction = mock(ProductTransaction.class);

        productHandler.discountStocks(transaction);

        verify(productServicePort, times(1)).discountStocks(transaction);
    }

    @Test
    void shouldCallSupplyProductOnProductServicePort() {
        SupplyRequest supplyRequest = mock(SupplyRequest.class);
        Supply supply = mock(Supply.class);
        when(productRequestMapper.toSupply(supplyRequest)).thenReturn(supply);

        productHandler.supplyProduct(supplyRequest);

        verify(productRequestMapper, times(1)).toSupply(supplyRequest);
        verify(productServicePort, times(1)).supplyProduct(supply);
    }

    @Test
    void shouldCallRestoreStockOnProductServicePort() {
        SupplyRequest supplyRequest = mock(SupplyRequest.class);
        Supply supply = mock(Supply.class);
        when(productRequestMapper.toSupply(supplyRequest)).thenReturn(supply);

        productHandler.restoreStock(supplyRequest);

        verify(productRequestMapper, times(1)).toSupply(supplyRequest);
        verify(productServicePort, times(1)).restoreStock(supply);
    }

    @Test
    void shouldReturnProductResponseWhenProductExists() {
        long productId = VALID_PRODUCT_ID;
        ProductResponse expectedResponse = mock(ProductResponse.class);
        when(productServicePort.getProductById(productId)).thenReturn(product);
        when(productResponseMapper.toResponse(product)).thenReturn(expectedResponse);

        ProductResponse actualResponse = productHandler.getProductById(productId);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(productServicePort, times(1)).getProductById(productId);
        verify(productResponseMapper, times(1)).toResponse(product);
    }
}