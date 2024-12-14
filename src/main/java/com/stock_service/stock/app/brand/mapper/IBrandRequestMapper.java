package com.stock_service.stock.app.brand.mapper;

import com.stock_service.stock.app.brand.dto.BrandRequest;
import com.stock_service.stock.domain.brand.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.stock_service.stock.domain.brand.util.BrandConstants.BRAND_ID;

@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {
    @Mapping(target = BRAND_ID, ignore = true)
    Brand toBrand(BrandRequest brandRequest);
}
