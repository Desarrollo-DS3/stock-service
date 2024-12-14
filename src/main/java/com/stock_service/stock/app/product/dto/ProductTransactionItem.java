package com.stock_service.stock.app.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductTransactionItem implements Serializable {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
