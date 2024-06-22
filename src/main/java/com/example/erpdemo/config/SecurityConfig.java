package com.example.erpdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//	@SuppressWarnings("deprecation")
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
////		http.csrf().disable().authorizeRequests().requestMatchers("/*").permitAll().and().sessionManagement()
////				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		http.csrf().disable().authorizeRequests().requestMatchers("/public/**").permitAll() // Public endpoints
//				.requestMatchers("/tenantPool/**").access(hasScope("read:user")) // Scoped endpoints
//				.anyRequest().authenticated().and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		return http.build();
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/public/**").permitAll()
				.requestMatchers("/tenantPool/**").hasAuthority("SCOPE_mps:admin").anyRequest().authenticated())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		return http.build();
	}

//	@Bean
//	public JwtDecoder jwtDecoder() {
//		// Replace with your Auth0 issuer URL and audience
//		String issuerUri = "https://mps-dev.auth0.com/";
//		String audience = "mps-dev-api";
//		NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuerUri);
////	        jwtDecoder.setJwtValidator(new AudienceValidator(audience));
//		return jwtDecoder;
//	}
}
