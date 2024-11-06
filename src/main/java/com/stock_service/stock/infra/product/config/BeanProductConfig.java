package com.stock_service.stock.infra.product.config;

import com.stock_service.stock.domain.product.api.IProductServicePort;
import com.stock_service.stock.domain.product.spi.IProductPersistencePort;
import com.stock_service.stock.domain.product.usecase.ProductUseCase;
import com.stock_service.stock.infra.product.out.IProductMapper;
import com.stock_service.stock.infra.product.out.IProductRepository;
import com.stock_service.stock.infra.product.out.ProductAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanProductConfig {
    private final IProductRepository productRepository;
    private final IProductMapper productMapper;

    @Bean
    public IProductPersistencePort productPersistence() {
        return new ProductAdapter(productRepository, productMapper);
    }

    @Bean
    public IProductServicePort productCreateService() {
        return new ProductUseCase(productPersistence());
    }
}
