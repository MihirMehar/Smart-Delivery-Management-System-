package com.orderservices.externalServices;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.orderservices.entity.Delivery;

@FeignClient(name = "delivery-service", url = "http://localhost:8586/delivery")
public interface DeliveryServices {
    
    @GetMapping("/{deliveryId}")
    Delivery getDeliveryById(@PathVariable String deliveryId);
    
    
    @GetMapping
    List<Delivery> getAllDeliveris();
    
}