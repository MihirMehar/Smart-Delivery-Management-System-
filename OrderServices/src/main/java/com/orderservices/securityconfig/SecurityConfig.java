package com.orderservices.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfig {
	
//	private static final String[] Public_Urls= {
//			"/v3/api-docs",
//			"swagger-resources/**",
//			"/swagger-ui/**",
//			"swagger-ui.html"
//	};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.requestMatchers("/actuator/**").permitAll()
		.requestMatchers(HttpMethod.GET,"/**").permitAll()
		  .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
		.anyRequest().authenticated()
		//require authentication of all req
		.and()
		.oauth2ResourceServer().jwt();
		
		return http.build();
	}

}
