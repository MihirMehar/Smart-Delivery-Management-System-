package com.configserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange()
                .pathMatchers("/public/**").permitAll() // Allow public access to certain endpoints
                .pathMatchers("/orders/**", "/delivery/**").authenticated() // Require authentication for /orders
                .anyExchange().authenticated() // All other requests require authentication
                .and()
            .oauth2ResourceServer()
                .jwt(); // Use JWT for resource server

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Optionally set a custom converter for authorities if needed
        return converter;
    }
}