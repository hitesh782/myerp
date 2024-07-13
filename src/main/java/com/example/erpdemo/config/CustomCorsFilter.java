package com.example.erpdemo.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomCorsFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization if needed
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		httpResponse.setHeader("Access-Control-Allow-Origin", "*"); // Allow all origins
		httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

		String tenantId = httpRequest.getHeader("x_tenant");
		if (tenantId == null || tenantId.isEmpty()) {
			tenantId = "ERP78";
		}
		TenantContext.setId(tenantId);

		if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
			httpResponse.setStatus(HttpServletResponse.SC_OK);
		} else {
			try {
				chain.doFilter(request, response);
			} finally {
				TenantContext.clear();
			}
		}
	}

	@Override
	public void destroy() {
		// Cleanup if needed
	}
}
