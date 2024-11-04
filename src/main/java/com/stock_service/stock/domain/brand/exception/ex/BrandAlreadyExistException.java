package com.stock_service.stock.domain.brand.exception.ex;

public class BrandAlreadyExistException extends RuntimeException {
    public BrandAlreadyExistException(String message) {
        super(message);
    }
}
