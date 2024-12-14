package com.stock_service.stock.infra.brand.output;

import com.stock_service.stock.domain.brand.model.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBrandMapper {
    BrandEntity toEntity(Brand brand);

    Brand toBrand(BrandEntity brandEntity);

    List<Brand> toBrands(List<BrandEntity> brandEntityList);
}
