package com.deliveryservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
@OpenAPIDefinition(
	    info = @Info(
	        title = "Delivery-Service API",
	        version = "1.0",
	        description = "API documentation for Order Microservice"
	    )
	)
@SpringBootApplication
@EnableFeignClients(basePackages = "com.deliveryservices.externalServices")
@EnableDiscoveryClient
public class DeliveryServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryServicesApplication.class, args);
	}

}
