package com.stock_service.stock.infra.exception.ex;

public class RollbackException extends RuntimeException {
  public RollbackException(String message) {
    super(message);
  }
}
