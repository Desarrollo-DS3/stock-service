package com.stock_service.stock.infra.product.config;

import com.stock_service.stock.domain.product.api.IProductServicePort;
import com.stock_service.stock.domain.product.spi.IProductNotifyPort;
import com.stock_service.stock.domain.product.spi.IProductPersistencePort;
import com.stock_service.stock.domain.product.usecase.ProductUseCase;
import com.stock_service.stock.infra.product.out.notify.MessageSender;
import com.stock_service.stock.infra.product.out.notify.ProductNotify;
import com.stock_service.stock.infra.product.out.persistence.IProductMapper;
import com.stock_service.stock.infra.product.out.persistence.IProductRepository;
import com.stock_service.stock.infra.product.out.persistence.ProductAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanProductConfig {
    private final IProductRepository productRepository;
    private final IProductMapper productMapper;
    private final MessageSender messageSender;

    @Bean
    public IProductPersistencePort productPersistence() {
        return new ProductAdapter(productRepository, productMapper);
    }

    @Bean
    public IProductNotifyPort productNotify() {
        return new ProductNotify(messageSender);
    }

    @Bean
    public IProductServicePort productCreateService() {
        return new ProductUseCase(productPersistence(), productNotify());
    }
}
