package com.stock_service.stock.app.brand.mapper;

import com.stock_service.stock.app.brand.dto.BrandResponse;
import com.stock_service.stock.domain.brand.model.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);
    List<BrandResponse> toBrandResponses(List<Brand> brands);
}
