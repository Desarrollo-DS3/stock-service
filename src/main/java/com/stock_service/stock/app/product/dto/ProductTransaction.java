package com.stock_service.stock.app.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductTransaction implements Serializable {
    private Long userId;
    private List<ProductTransactionItem> products;
}
