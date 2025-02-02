package com.orderservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.orderservices.entity.Delivery;
import com.orderservices.entity.Order;
import com.orderservices.entity.OrderStatus;
import com.orderservices.exception.OrderNotFoundException;
import com.orderservices.externalServices.DeliveryServices;
import com.orderservices.externalServices.NotificationService;
import com.orderservices.repository.OrderRepository;
import com.orderservices.services.OrderServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderServicesApplicationTests {

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;
	
	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private DeliveryServices deliveryServices;
	
	@Mock
	private NotificationService notificationService;
	
	private Order order;
	
	
	@BeforeEach
	void setup() {
		order = new Order();
		order.setOrderId("12345");
		order.setCustomerId(101L);
		order.setCustomerEmail("test@example.com");
		order.setOrderStatus(OrderStatus.DELIVERED);
		order.setTotalAmount(500.0);
		order.setShippingAddress("123 Test Street");
	}
	
	@Test
	void testCreatedOrder() {
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		
		Order createdOrder = orderServiceImpl.createOrder(order);
		assertNotNull(createdOrder);
		assertEquals(order.getOrderId(), createdOrder.getOrderId());
		verify(orderRepository,times(1)).save(any(Order.class));
	}
	
	
	@Test
	void testGetOrderById() throws OrderNotFoundException{
		when(orderRepository.findById("12345")).thenReturn(Optional.of(order));
		
		Order foundOrder = orderServiceImpl.getOrderById("12345");
		assertNotNull(foundOrder);
		assertEquals("12345", foundOrder.getOrderId());
		verify(orderRepository,times(1)).findById("12345");
	}
	
	@Test
	void testGetOrderById_notfound() throws OrderNotFoundException{
		when(orderRepository.findById("99999")).thenReturn(Optional.empty());
		
		assertThrows(OrderNotFoundException.class, ()->{
			orderServiceImpl.getOrderById("99999");
		});
		verify(orderRepository,times(1)).findById("99999");
	}
	
	@Test
	void testUpdatedOrder_Success() throws OrderNotFoundException{
		Order updatedOrder = new Order();
		updatedOrder.setOrderStatus(OrderStatus.SHIPPED);
		when(orderRepository.findById("12345")).thenReturn(Optional.of(order));
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		
		Order result =orderServiceImpl.updateOrder("12345", updatedOrder);
		assertNotNull(result);
		
		assertEquals(OrderStatus.SHIPPED, result.getOrderStatus());
		
		verify(orderRepository,times(1)).save(any(Order.class));
		
	}
	
	
	@Test
	void testUpdatedOrder_NotFound() throws OrderNotFoundException{
		when(orderRepository.findById("99999")).thenReturn(Optional.empty());
		Order updatedOrder = new Order();
		updatedOrder.setOrderStatus(OrderStatus.SHIPPED);
		assertThrows(OrderNotFoundException.class, ()->{
			orderServiceImpl.updateOrder("99999", updatedOrder);
		});
		verify(orderRepository,times(1)).findById("99999");
	}
	
	
	@Test
	void testdeleteOrder() throws OrderNotFoundException{
		when(orderRepository.findById("12345")).thenReturn(Optional.of(order));
		
		orderServiceImpl.deleteOrder("12345");
		
		verify(orderRepository,times(1)).deleteById("12345");
	}
	
	@Test
	void testDeleteOrder_NotFound(){
		when(orderRepository.findById("99999")).thenReturn(Optional.empty());
		assertThrows(OrderNotFoundException.class, ()->{
			orderServiceImpl.deleteOrder("99999");
		});
		verify(orderRepository,times(1)).findById("99999");
	}
	
	

		
	
	
}
