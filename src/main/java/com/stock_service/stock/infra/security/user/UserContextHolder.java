package com.stock_service.stock.infra.security.user;

public class UserContextHolder {
    private static final ThreadLocal<Long> userId = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        UserContextHolder.userId.set(userId);
    }

    public static Long getUserId() {
        return UserContextHolder.userId.get();
    }

    public static void clearUserId() {
        UserContextHolder.userId.remove();
    }
}
