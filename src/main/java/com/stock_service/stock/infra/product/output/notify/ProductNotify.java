package com.stock_service.stock.infra.product.output.notify;

import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.domain.product.model.Supply;
import com.stock_service.stock.domain.product.spi.IProductNotifyPort;

public class ProductNotify implements IProductNotifyPort {

    private final MessageSender messageSender;

    public ProductNotify(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void notifyRollback(ProductTransaction transaction) {
        messageSender.sendRollbackNotification(transaction);
    }

    @Override
    public void notifySale(ProductTransaction transaction) {
        messageSender.sendSaleNotification(transaction);
    }

    @Override
    public void notifySupply(Supply supply) {
        messageSender.sendSupplyNotification(supply);
    }
}
