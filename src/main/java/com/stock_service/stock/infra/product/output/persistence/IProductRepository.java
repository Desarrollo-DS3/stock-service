package com.stock_service.stock.infra.product.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

}
