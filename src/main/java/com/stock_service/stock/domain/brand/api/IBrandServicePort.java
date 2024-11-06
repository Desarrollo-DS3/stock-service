package com.stock_service.stock.domain.brand.api;

import com.stock_service.stock.domain.brand.model.Brand;
import jakarta.validation.constraints.NotNull;

public interface IBrandServicePort {
    void createBrand(Brand brand);
    Brand getBrandById(Long brandId);
}
