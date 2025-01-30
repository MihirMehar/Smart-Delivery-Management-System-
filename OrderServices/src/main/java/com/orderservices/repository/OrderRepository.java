package com.orderservices.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.orderservices.entity.Order;
import com.orderservices.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, String> {
	
	// creating some customer repository to perform some operation 
	
	List<Order> findByCustomerId(Long customerId);
	
	List<Order> findByOrderStatus(OrderStatus orderStatus);
	
	@Query("SELECT o FROM Order o where o.createdAt>:date AND o.isDeleted = false")
	List<Order> findOrdersCreatedAfter(@Param ("date") LocalDate date);
	
	// pagination
	Page<Order> findAll (Pageable pageable);
	

}
