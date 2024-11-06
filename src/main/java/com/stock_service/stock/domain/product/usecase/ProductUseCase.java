package com.stock_service.stock.domain.product.usecase;

import com.stock_service.stock.domain.brand.model.Brand;
import com.stock_service.stock.domain.category.model.Category;
import com.stock_service.stock.domain.product.api.IProductServicePort;
import com.stock_service.stock.domain.product.exception.ex.ProductNotValidFieldException;
import com.stock_service.stock.domain.product.model.Product;
import com.stock_service.stock.domain.product.spi.IProductPersistencePort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.stock_service.stock.domain.product.exception.ProductExceptionMessage.*;
import static com.stock_service.stock.domain.product.util.ProductConstants.*;

public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
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
}
