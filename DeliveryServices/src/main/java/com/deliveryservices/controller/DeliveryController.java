package com.deliveryservices.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryservices.entity.Delivery;
import com.deliveryservices.entity.DeliveryStatus;
import com.deliveryservices.exception.DeliveryNotFoundException;
import com.deliveryservices.services.DeliveryService;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

	@Autowired
	private DeliveryService deliveryService;

	private static final Logger logger = LoggerFactory.getLogger(DeliveryController.class);
	
	
	// post mapping
	@PostMapping
	public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
		logger.info(" + Received request to create delivery for order,{}", delivery.getOrderId());
		return ResponseEntity.status(HttpStatus.CREATED).body(deliveryService.createdelivery(delivery));
	}

	// get Mapping
	@GetMapping
	public ResponseEntity<List<Delivery>> getData() {
		logger.info(" + Received request to fetch all delivery for order,{}");
		return ResponseEntity.ok(deliveryService.getAllData());
	}

	@GetMapping("/{deliveryId}")
	public ResponseEntity<Delivery> getDatabyId(@PathVariable String deliveryId) throws DeliveryNotFoundException {
		logger.info(" + Received request to fetch delivery based on orderId,{}");
		return ResponseEntity.ok(deliveryService.getdeliveryByOrderId(deliveryId));
	}

	// update

	@PutMapping("/{deliveryId}")
	public ResponseEntity<String> updateDelivery(@PathVariable String deliveryId, @RequestBody Delivery delivery)
			throws DeliveryNotFoundException {

		// Check if the delivery object is null
		
		if (delivery == null) {
			return ResponseEntity.badRequest().body("Delivery object cannot be null.");
		}

		// Attempt to update the delivery
		Delivery updatedDelivery = deliveryService.updatedata(deliveryId, delivery);
		logger.info(" + Received request to update delivery for order,{}", delivery.getOrderId());
		if (updatedDelivery != null) {
			String message = "Delivery with ID " + deliveryId + " has been successfully updated.";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery with ID " + deliveryId + " not found.");
		}
	}

	@DeleteMapping("/{deliveryId}")
	public ResponseEntity<String> deleteOrder(@PathVariable String deliveryId) throws DeliveryNotFoundException {
		logger.info(" + Received request to delete delivery for order,{}");
		String message = "Delivery id is delete successfully on the server!";
		deliveryService.deletedata(deliveryId);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
	
	
	@GetMapping("/status/{deliveryStatus}")
	public ResponseEntity<List<Delivery>> getDeliveryByStatus(@PathVariable DeliveryStatus deliveryStatus){
		List<Delivery> newList = deliveryService.getDeliveryByStatus(deliveryStatus);
		return ResponseEntity.ok(newList);
	}
}
