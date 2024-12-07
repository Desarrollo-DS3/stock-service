package com.stock_service.stock.domain.brand.api;

import com.stock_service.stock.domain.brand.model.Brand;

import java.util.List;

public interface IBrandServicePort {
    void createBrand(Brand brand);

    Brand getBrandById(Long brandId);

    List<Brand> getAllBrands();
}
