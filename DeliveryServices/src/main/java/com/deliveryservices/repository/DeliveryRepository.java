package com.deliveryservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliveryservices.entity.Delivery;
import com.deliveryservices.entity.DeliveryStatus;

public interface DeliveryRepository  extends JpaRepository<Delivery, String>{
	
	
	// customer query
	
	List<Delivery> findByDeliveryStatus(DeliveryStatus deliveryStatus);

}
