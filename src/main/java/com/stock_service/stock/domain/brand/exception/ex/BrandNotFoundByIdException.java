package com.stock_service.stock.domain.brand.exception.ex;

public class BrandNotFoundByIdException extends RuntimeException {
    public BrandNotFoundByIdException(String message) {
        super(message);
    }
}
