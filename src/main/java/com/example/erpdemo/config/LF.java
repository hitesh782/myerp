package com.example.erpdemo.config;

public class LF {
	public static String format(final String message) {
		return format(message,null);
	}
	
	public static String format(final String message,String tenantId) {
		if(tenantId.isEmpty()) {
			tenantId="-1";
		}
		
		return String.format("[TENANT_ID: %s]   %s", tenantId,message);
		
	}
}
