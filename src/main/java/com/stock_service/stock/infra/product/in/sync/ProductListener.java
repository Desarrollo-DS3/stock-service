package com.stock_service.stock.infra.product.in.sync;

import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.app.product.dto.SupplyRequest;
import com.stock_service.stock.app.product.handler.IProductHandler;
import com.stock_service.stock.domain.product.model.Supply;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
public class ProductListener {
    private final IProductHandler productHandler;

    public ProductListener(IProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    @RabbitListener(queues = "purchase_discount_stock")
    public void notifyRegisterTransaction(ProductTransaction transaction) {
        productHandler.discountStocks(transaction);
    }

    @RabbitListener(queues = "purchase_restore_stock")
    public void restoreStockAndNotify(@Valid ProductTransaction transaction) {
        productHandler.restoreStocks(transaction);
    }

    @RabbitListener(queues = "supply_restore_stock")
    public void supplyStockAndNotify(@Valid SupplyRequest supplyRequest) {
        productHandler.restoreStock(supplyRequest);
    }
}
