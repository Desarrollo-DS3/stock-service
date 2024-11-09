package com.stock_service.stock.app.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplyRequest {
    @NotNull
    private Long productId;
    @NotNull
    @Positive
    private Integer quantity;
}
