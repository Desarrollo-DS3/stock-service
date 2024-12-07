package com.stock_service.stock.infra.category.in;

import com.stock_service.stock.app.category.dto.CategoryRequest;
import com.stock_service.stock.app.category.dto.CategoryResponse;
import com.stock_service.stock.app.category.handler.ICategoryHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
    private final ICategoryHandler categoryHandler;

    public CategoryController(ICategoryHandler categoryHandler) {
        this.categoryHandler = categoryHandler;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.createBrand(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> list = categoryHandler.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
