package com.stock_service.stock.infra.product.in.sync;

import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.app.product.dto.SupplyRequest;
import com.stock_service.stock.app.product.handler.IProductHandler;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
public class ProductListener {

    private static final Logger logger = LoggerFactory.getLogger(ProductListener.class);
    private final IProductHandler productHandler;

    public ProductListener(IProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    @RabbitListener(queues = "purchase_discount_stock")
    public void notifyRegisterTransaction(ProductTransaction transaction) {
        try {
            productHandler.discountStocks(transaction);
            logger.info("Stock discounted successfully for transaction: {}", transaction);
        } catch (Exception e) {
            logger.error("Error processing discount for transaction: {}", transaction, e);
        }
    }

    @RabbitListener(queues = "purchase_restore_stock")
    public void restoreStockAndNotify(@Valid ProductTransaction transaction) {
        try {
            productHandler.restoreStocks(transaction);
            logger.info("Stock restored successfully for transaction: {}", transaction);
        } catch (Exception e) {
            logger.error("Error restoring stock for transaction: {}", transaction, e);
        }
    }

    @RabbitListener(queues = "supply_restore_stock")
    public void supplyStockAndNotify(@Valid SupplyRequest supplyRequest) {
        try {
            productHandler.restoreStock(supplyRequest);
            logger.info("Stock restored successfully for supply: {}", supplyRequest);
        } catch (Exception e) {
            logger.error("Error restoring stock for supply: {}", supplyRequest, e);
        }
    }
}