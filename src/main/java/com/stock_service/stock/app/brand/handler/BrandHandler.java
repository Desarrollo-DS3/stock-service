package com.stock_service.stock.app.brand.handler;

import com.stock_service.stock.app.brand.dto.BrandRequest;
import com.stock_service.stock.app.brand.mapper.IBrandRequestMapper;
import com.stock_service.stock.domain.brand.api.IBrandServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandHandler implements IBrandHandler {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;

    @Override
    public void createBrand(BrandRequest brandRequest) {
        brandServicePort.createBrand(brandRequestMapper.toBrand(brandRequest));
    }
}
