package com.deliveryservices.services;

import java.util.List;

import com.deliveryservices.entity.Delivery;
import com.deliveryservices.entity.DeliveryStatus;
import com.deliveryservices.exception.DeliveryNotFoundException;

public interface DeliveryService {
	
	//create
	Delivery createdelivery(Delivery delivery);
	
	// get 
	List<Delivery> getAllData();
	
	// get delivery by id
	
	Delivery getdeliveryByOrderId(String deliveryId) throws DeliveryNotFoundException;
	
	// update
	Delivery updatedata(String deliveryId,Delivery delivery) throws DeliveryNotFoundException;
	
	//delete
	void deletedata(String deliveryId) throws DeliveryNotFoundException;
	
	//get list of delivery based on status
	List<Delivery> getDeliveryByStatus(DeliveryStatus deliveryStatus);
	

}
