package com.stock_service.stock.domain.brand.spi;

import com.stock_service.stock.domain.brand.model.Brand;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    Boolean isBrandPresentByName(String brandName);
}
