package com.stock_service.stock.infra.product.in.async;

import com.stock_service.stock.app.product.dto.ProductRequest;
import com.stock_service.stock.app.product.dto.SupplyRequest;
import com.stock_service.stock.app.product.handler.IProductHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
    private final IProductHandler productHandler;

    public ProductController(IProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        productHandler.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/supply")
    public ResponseEntity<Void> supplyProduct(@Valid @RequestBody SupplyRequest supplyRequest) {
        productHandler.supplyProduct(supplyRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
