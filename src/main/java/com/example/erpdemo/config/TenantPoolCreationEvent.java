package com.example.erpdemo.config;

public class TenantPoolCreationEvent {
    private String tenantPoolId;

    public TenantPoolCreationEvent(String tenantPoolId) {
        this.tenantPoolId = tenantPoolId;
    }

    public String getTenantPoolId() {
        return this.tenantPoolId;
    }
}
