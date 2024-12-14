package com.stock_service.stock.domain.product.spi;

import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.domain.product.model.Supply;

public interface IProductNotifyPort {
    void notifyRollback(ProductTransaction transaction);

    void notifySale(ProductTransaction transaction);

    void notifySupply(Supply supply);
}
