package com.stock_service.stock.infra.product.output.persistence;

import com.stock_service.stock.infra.brand.out.BrandEntity;
import com.stock_service.stock.infra.category.out.CategoryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.stock_service.stock.domain.product.exception.ProductExceptionMessage.NEGATIVE_PRICE;
import static com.stock_service.stock.domain.product.exception.ProductExceptionMessage.NEGATIVE_STOCK;
import static com.stock_service.stock.domain.product.util.ProductConstants.*;

@Entity
@Table(name = PRODUCT_TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    @Positive(message = NEGATIVE_PRICE)
    private BigDecimal price;

    @Positive(message = NEGATIVE_STOCK)
    private int stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = BRAND_ID_COLUMN, nullable = false)
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = CATEGORY_ID_COLUMN, nullable = false)
    private CategoryEntity category;
}
