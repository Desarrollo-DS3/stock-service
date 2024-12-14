package com.stock_service.stock.app.product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static com.stock_service.stock.domain.product.exception.ProductExceptionMessage.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = EMPTY_NAME)
    @NotEmpty(message = EMPTY_NAME)
    private String name;

    private String description;

    @NotNull(message = EMPTY_PRICE)
    @Positive(message = NEGATIVE_PRICE)
    private BigDecimal price;

    @NotNull(message = EMPTY_STOCK)
    @Positive(message = NEGATIVE_STOCK)
    private int stock;

    @NotNull(message = EMPTY_BRAND_ID)
    private Long brandId;

    @NotNull(message = EMPTY_CATEGORY_ID)
    private Long categoryId;
}
