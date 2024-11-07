package com.stock_service.stock.infra.brand.in;

import com.stock_service.stock.app.brand.dto.BrandRequest;
import com.stock_service.stock.app.brand.handler.IBrandHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand")
@Validated
public class BrandController {
    private static final String CREATE_BRAND_URL = "/create";

    private final IBrandHandler brandHandler;

    public BrandController(IBrandHandler brandHandler) {
        this.brandHandler = brandHandler;
    }

    @PostMapping(CREATE_BRAND_URL)
    ResponseEntity<Void> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        brandHandler.createBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
