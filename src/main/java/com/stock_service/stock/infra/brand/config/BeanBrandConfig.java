package com.stock_service.stock.infra.brand.config;

import com.stock_service.stock.domain.brand.api.IBrandServicePort;
import com.stock_service.stock.domain.brand.spi.IBrandPersistencePort;
import com.stock_service.stock.domain.brand.usecase.BrandUseCase;
import com.stock_service.stock.infra.brand.output.BrandAdapter;
import com.stock_service.stock.infra.brand.output.IBrandMapper;
import com.stock_service.stock.infra.brand.output.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanBrandConfig {
    private final IBrandRepository brandRepository;
    private final IBrandMapper brandMapper;

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandAdapter(brandRepository, brandMapper);
    }

    @Bean
    public IBrandServicePort brandCreateServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }
}
