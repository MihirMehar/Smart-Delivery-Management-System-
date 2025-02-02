package com.orderservices.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orderservices.entity.Order;
import com.orderservices.entity.OrderStatus;
import com.orderservices.exception.OrderNotFoundException;

public interface OrderService {
	
	// create
	
	Order createOrder(Order order);
	
	// get
	
	List<Order> getAllOrders();
	
	// get Order by Id
	
	Order getOrderById(String orderId) throws OrderNotFoundException;

	
	// update
	Order updateOrder(String orderId,Order order) throws OrderNotFoundException;
	
//	// delete
	void deleteOrder(String orderId) throws OrderNotFoundException;
	
	// get order by customerId
	List<Order> getOrdersByCustomerId(Long customerId);
	
	// get order by status
	List<Order> getOrdersByStatus(OrderStatus orderStatus);
	
	// List order by craeted AT
	List<Order> getOrdersCreatedAfter(LocalDate date);
	
	//pagination
	Page<Order> getAllOrders(Pageable pageable);
	

}
