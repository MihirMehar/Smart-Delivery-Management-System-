package com.orderservices.externalServices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.orderservices.entity.NotificationRequest;

@FeignClient(name = "notifiaction-Service",url = "http://localhost:8587/notifications")
public interface NotificationService {
	
	@PostMapping
	void sendNotifiactions(@RequestBody NotificationRequest notificationRequest);

}
