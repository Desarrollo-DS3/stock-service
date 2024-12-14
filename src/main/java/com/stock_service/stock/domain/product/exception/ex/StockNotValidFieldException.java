package com.stock_service.stock.domain.product.exception.ex;

public class StockNotValidFieldException extends RuntimeException {
    public StockNotValidFieldException(String message) {
        super(message);
    }
}
