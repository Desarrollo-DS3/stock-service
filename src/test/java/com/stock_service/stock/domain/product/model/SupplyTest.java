package com.stock_service.stock.domain.product.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplyTest {

    @Test
    void shouldSetAndGetSupplyFieldsSuccessfully() {
        Long userId = 1L;
        Long productId = 100L;
        Integer quantity = 50;

        Supply supply = new Supply(userId, productId, quantity);

        assertEquals(userId, supply.getUserId());
        assertEquals(productId, supply.getProductId());
        assertEquals(quantity, supply.getQuantity());
    }

    @Test
    void shouldUpdateSupplyFieldsSuccessfully() {
        Supply supply = new Supply(1L, 100L, 50);

        supply.setUserId(2L);
        supply.setProductId(200L);
        supply.setQuantity(75);

        assertEquals(2L, supply.getUserId());
        assertEquals(200L, supply.getProductId());
        assertEquals(75, supply.getQuantity());
    }

    @Test
    void shouldNotAllowNullValuesForSupplyFields() {
        Supply supply = new Supply(null, null, null);

        assertNull(supply.getUserId());
        assertNull(supply.getProductId());
        assertNull(supply.getQuantity());
    }
}
