package com.stock_service.stock.app.brand.handler;

import com.stock_service.stock.app.brand.dto.BrandRequest;
import com.stock_service.stock.app.brand.dto.BrandResponse;
import com.stock_service.stock.app.brand.mapper.IBrandRequestMapper;
import com.stock_service.stock.app.brand.mapper.IBrandResponseMapper;
import com.stock_service.stock.domain.brand.api.IBrandServicePort;
import com.stock_service.stock.domain.brand.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.stock_service.stock.utils.TestConstants.*;
import static org.mockito.Mockito.*;

class BrandHandlerTest {

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;

    @Mock
    private IBrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandHandler brandHandler;

    private Brand brand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        brand = new Brand(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }

    @Test
    void shouldCallCreateBrandOnBrandCreateService(){
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName(VALID_BRAND_NAME);
        brandRequest.setDescription(VALID_BRAND_DESCRIPTION);

        when(brandRequestMapper.toBrand(brandRequest)).thenReturn(brand);

        brandHandler.createBrand(brandRequest);

        verify(brandServicePort, times(1)).createBrand(brand);
    }

    @Test
    void shouldReturnBrandListWhenGetAllCategories(){
        BrandResponse brandResponse = new BrandResponse(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);

        when(brandResponseMapper.toBrandResponses(List.of(brand))).thenReturn(List.of(brandResponse));

        brandHandler.getAllBrands();

        verify(brandServicePort, times(1)).getAllBrands();
    }

}