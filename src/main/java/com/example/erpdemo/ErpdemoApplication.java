package com.example.erpdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.erpdemo.config.CustomCorsFilter;

@SpringBootApplication
@EnableMongoAuditing
@EnableScheduling
//@EnableMethodSecurity
public class ErpdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpdemoApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<CustomCorsFilter> corsFilterRegistrationBean() {
		FilterRegistrationBean<CustomCorsFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new CustomCorsFilter());
		registrationBean.addUrlPatterns("/*"); // Apply filter to all URL patterns
		registrationBean.setOrder(0); // Set precedence of the filter
		return registrationBean;
	}

}
