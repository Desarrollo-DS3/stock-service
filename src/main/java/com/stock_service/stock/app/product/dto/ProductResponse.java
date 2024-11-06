package com.stock_service.stock.app.product.dto;

import com.stock_service.stock.app.brand.dto.BrandResponse;
import com.stock_service.stock.app.category.dto.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private BrandResponse brand;
    private CategoryResponse category;
}
