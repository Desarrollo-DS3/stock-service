package com.stock_service.stock.domain.brand.exception;

import com.stock_service.stock.domain.brand.util.BrandConstants;

public final class BrandExceptionMessage {
    public static final String TOO_LONG_NAME =
            "The 'name' field is too long. " +
                    "The maximum length allowed is '" + BrandConstants.MAX_NAME_LENGTH + "' characters";
    public static final String TOO_LONG_DESCRIPTION =
            "The 'description' field is too long. " +
                    "The maximum length allowed is '" + BrandConstants.MAX_DESCRIPTION_LENGTH + "' characters";
    public static final String EMPTY_NAME =
            "The 'name' field is empty";
    public static final String EMPTY_DESCRIPTION =
            "The 'description' field is empty";
    public static final String ALREADY_EXIST_BRAND =
            "Brand already exists";
    public static final String NO_FOUND_BRAND =
            "Brand does not found";

    private BrandExceptionMessage() {
        throw new AssertionError();
    }

}
