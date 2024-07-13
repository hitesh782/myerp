package com.example.erpdemo.config;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventListeners {

    @EventListener
    public void handleTenantPoolCreationEvent(TenantPoolCreationEvent event) {
        System.out.println("tenant pool created: " + event.getTenantPoolId());
    }
}
