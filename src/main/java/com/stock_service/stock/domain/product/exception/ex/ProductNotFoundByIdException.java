package com.stock_service.stock.domain.product.exception.ex;

public class ProductNotFoundByIdException extends RuntimeException {
    public ProductNotFoundByIdException(String message) {
        super(message);
    }
}
