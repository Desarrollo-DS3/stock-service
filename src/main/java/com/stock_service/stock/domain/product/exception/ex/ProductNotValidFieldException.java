package com.stock_service.stock.domain.product.exception.ex;

public class ProductNotValidFieldException extends RuntimeException {
    public ProductNotValidFieldException(String message) {
        super(message);
    }
}
