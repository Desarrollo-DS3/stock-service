package com.stock_service.stock.domain.brand.spi;

import com.stock_service.stock.domain.brand.model.Brand;

import java.util.Optional;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    Boolean isBrandPresentByName(String brandName);
    Optional<Brand> getBrandById(Long id);
}
