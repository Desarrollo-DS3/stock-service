package com.stock_service.stock.domain.product.usecase;

import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.app.product.dto.ProductTransactionItem;
import com.stock_service.stock.domain.brand.model.Brand;
import com.stock_service.stock.domain.category.model.Category;
import com.stock_service.stock.domain.product.api.IProductServicePort;
import com.stock_service.stock.domain.product.exception.ex.InsufficientStockException;
import com.stock_service.stock.domain.product.exception.ex.ProductNotFoundByIdException;
import com.stock_service.stock.domain.product.exception.ex.ProductNotValidFieldException;
import com.stock_service.stock.domain.product.exception.ex.StockNotValidFieldException;
import com.stock_service.stock.domain.product.model.Product;
import com.stock_service.stock.domain.product.model.Supply;
import com.stock_service.stock.domain.product.spi.IProductNotifyPort;
import com.stock_service.stock.domain.product.spi.IProductPersistencePort;
import com.stock_service.stock.infra.exception.ex.NotificationException;
import com.stock_service.stock.infra.exception.ex.RollbackException;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.stock_service.stock.domain.product.exception.ProductExceptionMessage.*;
import static com.stock_service.stock.domain.product.util.ProductConstants.MIN_PRICE;
import static com.stock_service.stock.domain.product.util.ProductConstants.MIN_STOCK;

public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;
    private final IProductNotifyPort productNotifyPort;

    public ProductUseCase(IProductPersistencePort productPersistencePort, IProductNotifyPort productNotifyPort) {
        this.productPersistencePort = productPersistencePort;
        this.productNotifyPort = productNotifyPort;
    }

    @Override
    public void createProduct(Product product) {
        String productName = product.getName();
        BigDecimal price = product.getPrice();
        Integer stock = product.getStock();
        Brand brand = product.getBrand();
        Category category = product.getCategory();

        if (productName == null || productName.trim().isEmpty()) {
            throw new ProductNotValidFieldException(EMPTY_NAME);
        }

        if (price == null) {
            throw new ProductNotValidFieldException(EMPTY_PRICE);
        }

        if (price.compareTo(BigDecimal.ZERO) < MIN_PRICE) {
            throw new ProductNotValidFieldException(NEGATIVE_PRICE);
        }

        if (stock == null) {
            throw new ProductNotValidFieldException(EMPTY_STOCK);
        }

        if (stock < MIN_STOCK) {
            throw new ProductNotValidFieldException(NEGATIVE_STOCK);
        }

        if (brand == null) {
            throw new ProductNotValidFieldException(EMPTY_BRAND_ID);
        }

        if (category == null) {
            throw new ProductNotValidFieldException(EMPTY_CATEGORY_ID);
        }

        productPersistencePort.createProduct(product);
    }

    private void validateProductId(Long productId) {
        if (productId == null) {
            throw new StockNotValidFieldException(EMPTY_PRODUCT);
        }
    }

    private Product findProduct(Long productId) {
        return productPersistencePort.getProductById(productId)
                .orElseThrow(() -> new ProductNotFoundByIdException(NO_FOUND_PRODUCT));
    }

    @Override
    public void updateStock(Long productId, int amount) {
        validateProductId(productId);
        Product product = findProduct(productId);
        product.setStock(product.getStock() + amount);
        productPersistencePort.updateProduct(product);
    }

    @Override
    public void restoreStocks(ProductTransaction transaction) {
        for (ProductTransactionItem transactionItem : transaction.getProducts()) {
            Long productId = transactionItem.getProductId();
            int amount = transactionItem.getQuantity();
            updateStock(productId, amount);
        }

        productNotifyPort.notifyRollback(transaction);
    }

    private void discountStock(Long productId, int amount) {
        Product product = findProduct(productId);
        if (product.getStock() < amount) {
            throw new InsufficientStockException("Insufficient stock for product ID: " + productId);
        }

        product.setStock(product.getStock() - amount);

        productPersistencePort.updateProduct(product);
    }

    @Override
    public void discountStocks(ProductTransaction transaction) {
        try {
            for (ProductTransactionItem transactionItem : transaction.getProducts()) {
                Long productId = transactionItem.getProductId();
                int amount = transactionItem.getQuantity();
                discountStock(productId, amount);
            }

            productNotifyPort.notifySale(transaction);
        } catch (Exception e) {
            productNotifyPort.notifyRollback(transaction);
            throw e;
        }
    }

    @Override
    @Transactional
    public void supplyProduct(Supply supply) {
        Product product = findProduct(supply.getProductId());
        Integer prevStock = product.getStock();
        product.setStock(product.getStock() + supply.getQuantity());

        productPersistencePort.updateProduct(product);

        try {
            supply.setUserId(1L);
            productNotifyPort.notifySupply(supply);
        } catch (Exception e) {
            product.setStock(prevStock);
            productPersistencePort.updateProduct(product);
            throw new NotificationException("Failed to send supply notification for product ID: "
                    + supply.getProductId() + ". Stock has been reverted. Reason: " + e.getMessage());
        }
    }

    @Override
    public void restoreStock(Supply supply) {
        Long productId = supply.getProductId();
        Integer quantity = supply.getQuantity();
        validateProductId(productId);
        Product product = findProduct(productId);
        product.setStock(product.getStock() - quantity);
        productPersistencePort.updateProduct(product);

        throw new RollbackException("Rollback triggered: stock update for product ID "
                + productId + " reverted due to insufficient stock or invalid supply quantity: " + quantity);
    }

    @Override
    public List<Product> getAllProducts() {
        return productPersistencePort.getAllProducts();
    }

    @Override
    public Product getProductById(long productId) {
        return productPersistencePort.getProductById(productId).orElseThrow(() -> new ProductNotFoundByIdException(NO_FOUND_PRODUCT));
    }
}
