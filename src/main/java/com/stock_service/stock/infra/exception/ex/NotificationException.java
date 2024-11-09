package com.stock_service.stock.infra.exception.ex;

public class NotificationException extends RuntimeException {
    public NotificationException(String message) {
        super(message);
    }
}
