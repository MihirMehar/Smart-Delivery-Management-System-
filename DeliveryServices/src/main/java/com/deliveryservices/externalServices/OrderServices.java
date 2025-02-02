package com.deliveryservices.externalServices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.deliveryservices.entity.Order;

@FeignClient(name = "orderservices",url = "http://localhost:8585/orders")
public interface OrderServices {
	
	@GetMapping("/{orderId}")
	Order getOrderById(@PathVariable String orderId);

}
