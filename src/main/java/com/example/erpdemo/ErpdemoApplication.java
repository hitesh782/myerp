package com.example.erpdemo;

import java.time.Duration;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.example.erpdemo.config.CustomCorsFilter;

@SpringBootApplication
@EnableMongoAuditing
@EnableScheduling
// @EnableMethodSecurity
public class ErpdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpdemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		return builder.setConnectTimeout(Duration.ofMillis(3000)).setReadTimeout(Duration.ofMillis(3000)).build();

		// return new RestTemplate();
	}

	@Bean
	public Javers javersTemplate() {
		return JaversBuilder.javers().build();
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
