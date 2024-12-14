package com.stock_service.stock.domain.brand.util;

public class BrandConstants {
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_DESCRIPTION_LENGTH = 120;
    public static final String BRAND_ID = "id";
    public static final String NAME = "name";
    public static final String BRAND_TABLE_NAME = "brand";

    private BrandConstants() {
        throw new AssertionError();
    }
}
