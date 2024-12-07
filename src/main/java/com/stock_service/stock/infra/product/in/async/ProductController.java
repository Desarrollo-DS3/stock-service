package com.stock_service.stock.infra.product.in.async;

import com.stock_service.stock.app.product.dto.ProductRequest;
import com.stock_service.stock.app.product.dto.ProductResponse;
import com.stock_service.stock.app.product.dto.SupplyRequest;
import com.stock_service.stock.app.product.handler.IProductHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> list = productHandler.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponse> getProductById(@Valid @PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productHandler.getProductById(id));
    }
}
