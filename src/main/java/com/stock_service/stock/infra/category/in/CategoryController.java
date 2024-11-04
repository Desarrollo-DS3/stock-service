package com.stock_service.stock.infra.category.in;

import com.stock_service.stock.app.category.dto.CategoryRequest;
import com.stock_service.stock.app.category.handler.ICategoryHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
    private static final String CREATE_CATEGORY_URL = "/create";

    private final ICategoryHandler categoryHandler;

    public CategoryController(ICategoryHandler categoryHandler) {
        this.categoryHandler = categoryHandler;
    }

    @PostMapping(CREATE_CATEGORY_URL)
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.createBrand(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
