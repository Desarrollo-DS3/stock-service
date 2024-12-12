package com.stock_service.stock.infra.security.jwt;

import static com.stock_service.stock.domain.util.GlobalConstants.TOKEN_SUBSTRING;

public class JwtUtility {
    private JwtUtility() {
        throw new AssertionError();
    }

    public static String extractJwt(String authHeader) {
        return authHeader.substring(TOKEN_SUBSTRING);
    }
}
