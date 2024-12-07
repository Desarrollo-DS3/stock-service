package com.stock_service.stock.infra.brand.in;

import com.stock_service.stock.app.brand.dto.BrandRequest;
import com.stock_service.stock.app.brand.dto.BrandResponse;
import com.stock_service.stock.app.brand.handler.IBrandHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@Validated
public class BrandController {
    private final IBrandHandler brandHandler;

    public BrandController(IBrandHandler brandHandler) {
        this.brandHandler = brandHandler;
    }

    @PostMapping("/create")
    ResponseEntity<Void> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        brandHandler.createBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    ResponseEntity<List<BrandResponse>> getAllBrands() {
        List<BrandResponse> list = brandHandler.getAllBrands();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
