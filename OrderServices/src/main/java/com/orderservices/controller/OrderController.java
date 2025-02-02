package com.orderservices.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderservices.entity.NotificationRequest;
import com.orderservices.entity.Order;
import com.orderservices.entity.OrderStatus;
import com.orderservices.exception.OrderNotFoundException;
import com.orderservices.externalServices.NotificationService;
import com.orderservices.services.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private NotificationService notificationService;

	@PostMapping
	public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
//		Order o1 = orderService.createOrder(order);
		Order savedOrder = orderService.createOrder(order);

		// Prepare notification
		NotificationRequest nR = new NotificationRequest();
		nR.setTo(order.getCustomerEmail());
		nR.setSubject("Order Confirmation");
		nR.setBody("Your order with ID : " + savedOrder.getOrderId()+ ":" +savedOrder.getProductDetails() + " has been successfully placed !");

		try {
			notificationService.sendNotifiactions(nR);
		} catch (Exception e) {
			System.out.println("Failed to send notification" + e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
	}

	@GetMapping@Operation(summary = "all orders detials ",description = "Fetches details of a specfice order.")
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> allOrders = orderService.getAllOrders();
		return ResponseEntity.ok(allOrders);
	}

	@Operation(summary = "get order by Id",description = "Fetches details of a specfice order using its ID.")
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable String orderId) throws OrderNotFoundException {
		Order o1 = orderService.getOrderById(orderId);
		return ResponseEntity.ok(o1);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<String> updateOrder(@PathVariable String orderId, @Valid @RequestBody Order order)
			throws OrderNotFoundException {
		if (orderId == null || orderId.isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid order ID.");
		}
		if (order == null) {
			return ResponseEntity.badRequest().body("Order details cannot be null.");
		}

		orderService.updateOrder(orderId, order);
		String message = "Order with ID " + orderId + " has been successfully updated.";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> deleteOrder(@PathVariable String orderId) throws OrderNotFoundException {
		String message = "Order id is delete successfully on the server!";
		orderService.deleteOrder(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	// some extra functionality get orders by cusrtomerId
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
		List<Order> orders = orderService.getOrdersByCustomerId(customerId);
		return ResponseEntity.ok(orders);
	}

	// some extra functionality get orders by status
	@GetMapping("/status/{orderStatus}")
	public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus orderStatus) {
		List<Order> orders = orderService.getOrdersByStatus(orderStatus);
		return ResponseEntity.ok(orders);
	}

	// get orders created after date

	@GetMapping("/created-after/{date}")
	public ResponseEntity<List<Order>> getOrdersCreatedAfter(@PathVariable String date) {
		LocalDate parsedDate = LocalDate.parse(date);
		List<Order> orders = orderService.getOrdersCreatedAfter(parsedDate);
		return ResponseEntity.ok(orders);
	}

	// pagination
	@GetMapping("/")
	public Page<Order> getAllOrder(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return orderService.getAllOrders(pageable);
	}
}
