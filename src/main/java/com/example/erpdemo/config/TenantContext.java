package com.example.erpdemo.config;

public class TenantContext {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setId(String tenantId) {
        CONTEXT.set(tenantId);
    }

    public static String getId() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
