package com.stock_service.stock.domain.product.exception.ex;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
