package com.orderservices.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orderservices.entity.Delivery;
import com.orderservices.entity.NotificationRequest;
import com.orderservices.entity.Order;
import com.orderservices.entity.OrderStatus;
import com.orderservices.exception.OrderNotFoundException;
import com.orderservices.externalServices.DeliveryServices;
import com.orderservices.externalServices.NotificationService;
import com.orderservices.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DeliveryServices deliveryServices;

	@Autowired
	private NotificationService notificationService;

	@Override
	public Order createOrder(Order order) {
		// TODO Auto-generated method stub
		String randomId = UUID.randomUUID().toString();
		order.setOrderId(randomId);
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public Order getOrderById(String orderId) throws OrderNotFoundException {

		return orderRepository.findById(orderId).orElseThrow(
				() -> new OrderNotFoundException("Order with given details are not present on the server"));
	}

	@Override
	public Order updateOrder(String orderId, Order order) throws OrderNotFoundException {
		Optional<Order> optional = orderRepository.findById(orderId);
		if (optional.isPresent()) {
			Order existingOrder = optional.get();

			// Update fields only if they are not null in the provided order object
			if (order.getOrderId() != null) {
				existingOrder.setOrderId(order.getOrderId());
			}
			if (order.getCustomerId() != null) {
				existingOrder.setCustomerId(order.getCustomerId());
			}
			if (order.getCustomerEmail() != null) {
				existingOrder.setCustomerEmail(order.getCustomerEmail());
			}
			if (order.getOrderStatus() != null) {
				existingOrder.setOrderStatus(order.getOrderStatus());
			}
			if (order.getCreatedAt() != null) {
				existingOrder.setCreatedAt(order.getCreatedAt());
			}
			if (order.getUpdatedAt() != null) {
				existingOrder.setCreatedAt(order.getUpdatedAt());
			}
			if (order.getProductDetails() != null) {
				existingOrder.setProductDetails(order.getProductDetails());
			}
			if (order.getTotalAmount() != null) {
				existingOrder.setTotalAmount(order.getTotalAmount());
			}
			if (order.getShippingAddress() != null) {
				existingOrder.setShippingAddress(order.getShippingAddress());
			}

			// Save the updated order back to the repository
			Order updatedOrder = orderRepository.save(existingOrder);

			// Send notification about the order update
			NotificationRequest notificationRequest = new NotificationRequest();
			notificationRequest.setTo(existingOrder.getCustomerEmail());
			notificationRequest.setSubject("Your Order Has Been Updated");
			notificationRequest.setBody(
					"Your order with ID " + orderId + " has been updated. Please check your account for details.");
			notificationRequest.setNotificationType("EMAIL");
		

			// Call the notification service to send the email
			notificationService.sendNotifiactions(notificationRequest);

			return updatedOrder;
		} else {
			throw new OrderNotFoundException("Order not found with ID: " + orderId);
		}

	}

	@Override
	public void deleteOrder(String orderId) throws OrderNotFoundException {
		Optional<Order> optional = Optional.ofNullable(orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order with given id is unavailbale on the server !")));
		orderRepository.deleteById(orderId);
	}

	@Override
	@CircuitBreaker(name = "orderService", fallbackMethod = "fallbackGetOrdersByCustomerId")
	public List<Order> getOrdersByCustomerId(Long customerId) {
		// Fetch orders by customer ID
		List<Order> orders = orderRepository.findByCustomerId(customerId);

		// Fetch all deliveries from the delivery service
		List<Delivery> allDeliveries = deliveryServices.getAllDeliveris();

		// Enrich each order with its corresponding deliveries
		for (Order order : orders) {
			List<Delivery> deliveriesForOrder = allDeliveries.stream()
					.filter(delivery -> delivery.getOrderId().equals(order.getOrderId())).collect(Collectors.toList());

			// Set the filtered deliveries for the current order
			order.setDelivery(deliveriesForOrder); // Ensure you set the correct list of deliveries
		}

		return orders; // Return the enriched list of orders
	}

	// fallback
	// Fallback method
	public List<Order> fallbackGetOrdersByCustomerId(Long customerId, Throwable throwable) {
		// Log the error or handle it as needed
		System.out.println("Fallback method called due to: " + throwable.getMessage());

		// Return a dummy response or a message indicating the service is down
		Order dummyOrder = new Order();
		dummyOrder.setOrderId("dummy12345");
		dummyOrder.setCustomerId(customerId);
		dummyOrder.setProductDetails("Service is currently down. Please try again later.");
		dummyOrder.setTotalAmount(0.0);
		dummyOrder.setOrderStatus(OrderStatus.PENDING);
		dummyOrder.setShippingAddress("N/A");
		dummyOrder.setDelivery(Collections.emptyList()); // No deliveries available

		return Collections.singletonList(dummyOrder); // Return a list with the dummy order
	}

	@Override
	public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
		// TODO Auto-generated method stub
		return orderRepository.findByOrderStatus(orderStatus);
	}

	@Override
	public List<Order> getOrdersCreatedAfter(LocalDate date) {
		// TODO Auto-generated method stub
		return orderRepository.findOrdersCreatedAfter(date);
	}

	@Override
	public Page<Order> getAllOrders(Pageable pageable) {
		// TODO Auto-generated method stub
		return orderRepository.findAll(pageable);
	}



}
