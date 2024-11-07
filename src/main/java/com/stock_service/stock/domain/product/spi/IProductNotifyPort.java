package com.stock_service.stock.domain.product.spi;

import com.stock_service.stock.app.product.dto.ProductTransaction;

public interface IProductNotifyPort {
    void notifyRollback(ProductTransaction transaction);

    void notifyTransaction(ProductTransaction transaction);
}
