package com.stock_service.stock.infra.brand.output;

import com.stock_service.stock.domain.brand.model.Brand;
import com.stock_service.stock.domain.brand.spi.IBrandPersistencePort;

import java.util.Optional;

public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandMapper brandMapper;

    public BrandAdapter(IBrandRepository brandRepository, IBrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public void createBrand(Brand brand) {
        brandRepository.save(brandMapper.toEntity(brand));
    }

    @Override
    public Boolean isBrandPresentByName(String brandName) {
        return brandRepository.findByName(brandName).isPresent();
    }

    @Override
    public Optional<Brand> getBrandById(Long brandId) {
        return brandRepository.findById(brandId).map(brandMapper::toBrand);
    }
}
