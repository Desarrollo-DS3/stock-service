package com.stock_service.stock.domain.category.util;

public final class CategoryConstants {
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_DESCRIPTION_LENGTH = 90;
    public static final String NAME = "name";
    public static final String CATEGORY_TABLE_NAME = "category";

    private CategoryConstants() {
        throw new AssertionError();
    }
}
