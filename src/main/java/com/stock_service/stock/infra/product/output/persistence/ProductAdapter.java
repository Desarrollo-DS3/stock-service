package com.stock_service.stock.infra.product.output.persistence;

import com.stock_service.stock.domain.product.model.Product;
import com.stock_service.stock.domain.product.spi.IProductPersistencePort;

import java.util.List;
import java.util.Optional;

public class ProductAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final IProductMapper productMapper;

    public ProductAdapter(IProductRepository productRepository, IProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(productMapper.toEntity(product));
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toProduct);
    }

    @Override
    public void updateProduct(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        productRepository.save(productEntity);
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.toProductList(productRepository.findAll());
    }
}
