package com.stock_service.stock.domain.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Supply {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
