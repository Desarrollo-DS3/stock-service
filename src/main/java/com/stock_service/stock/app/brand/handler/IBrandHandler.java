package com.stock_service.stock.app.brand.handler;

import com.stock_service.stock.app.brand.dto.BrandRequest;
import com.stock_service.stock.app.brand.dto.BrandResponse;

import java.util.List;

public interface IBrandHandler {
    void createBrand(BrandRequest brandRequest);

    List<BrandResponse> getAllBrands();
}
