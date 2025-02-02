package com.deliveryservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.deliveryservices.entity.Delivery;
import com.deliveryservices.entity.DeliveryStatus;
import com.deliveryservices.entity.Order;
import com.deliveryservices.exception.DeliveryNotFoundException;
import com.deliveryservices.externalServices.OrderServices;
import com.deliveryservices.repository.DeliveryRepository;
import com.deliveryservices.services.DeliveryServiceImpl;

@SpringBootTest
class DeliveryServicesApplicationTests {

	 @InjectMocks
	    private DeliveryServiceImpl deliveryService;
	 
	 @Mock
	    private DeliveryRepository deliveryRepository;

	  @Mock
	    private OrderServices orderServices;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testCreateDelivery() {
	        Delivery delivery = new Delivery();
	        delivery.setOrderId("order-123");

	        Order order = new Order();
	        order.setOrderStatus("SHIPPED");
	        order.setShippingAddress("123 Main St, Anytown USA");

	        when(orderServices.getOrderById("order-123")).thenReturn(order);
	        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);

	        Delivery savedDelivery = deliveryService.createdelivery(delivery);

	        assertNotNull(savedDelivery.getDeliveryId());
	        assertEquals(DeliveryStatus.IN_TRANSI, savedDelivery.getDeliveryStatus());
	        assertEquals(order.getShippingAddress(), savedDelivery.getAddress());
	        assertNotNull(savedDelivery.getEstimatedDeliveyDate());
	        assertNotNull(savedDelivery.getActualDeliveryDate());
	    }

	    @Test
	    void testGetAllData() {
	        List<Delivery> deliveries = new ArrayList<>();
	        deliveries.add(new Delivery());
	        deliveries.add(new Delivery());

	        when(deliveryRepository.findAll()).thenReturn(deliveries);

	        List<Delivery> result = deliveryService.getAllData();

	        assertEquals(2, result.size());
	    }

	    @Test
	    void testGetDeliveryByOrderId() throws DeliveryNotFoundException {
	        Delivery delivery = new Delivery();
	        delivery.setDeliveryId("delivery-123");

	        when(deliveryRepository.findById("delivery-123")).thenReturn(Optional.of(delivery));

	        Delivery result = deliveryService.getdeliveryByOrderId("delivery-123");

	        assertEquals(delivery, result);
	    }

	    @Test
	    void testGetDeliveryByOrderIdNotFound() {
	        when(deliveryRepository.findById("delivery-123")).thenReturn(Optional.empty());

	        assertThrows(DeliveryNotFoundException.class, () -> deliveryService.getdeliveryByOrderId("delivery-123"));
	    }

	    @Test
	    void testUpdateData() throws DeliveryNotFoundException {
	        Delivery existingDelivery = new Delivery();
	        existingDelivery.setDeliveryId("delivery-123");
	        existingDelivery.setOrderId("order-123");
	        existingDelivery.setAddress("123 Main St, Anytown USA");
	        existingDelivery.setTrackingNumber("TRACK123");
	        existingDelivery.setShoppingProvider("UPS");
	        existingDelivery.setEstimatedDeliveyDate(LocalDate.now().plusDays(3));
	        existingDelivery.setActualDeliveryDate(LocalDate.now().plusDays(2));
	        existingDelivery.setDelivered(false);

	        Delivery updatedDelivery = new Delivery();
	        updatedDelivery.setOrderId("order-456");
	        updatedDelivery.setAddress("456 Oak St, Anytown USA");
	        updatedDelivery.setTrackingNumber("TRACK456");
	        updatedDelivery.setShoppingProvider("FedEx");
	        updatedDelivery.setEstimatedDeliveyDate(LocalDate.now().plusDays(5));
	        updatedDelivery.setActualDeliveryDate(LocalDate.now().plusDays(4));
	        updatedDelivery.setDelivered(true);

	        when(deliveryRepository.findById("delivery-123")).thenReturn(Optional.of(existingDelivery));
	        when(deliveryRepository.save(any(Delivery.class))).thenReturn(updatedDelivery);

	        Delivery result = deliveryService.updatedata("delivery-123", updatedDelivery);

	        assertEquals("order-456", result.getOrderId());
	        assertEquals("456 Oak St, Anytown USA", result.getAddress());
	        assertEquals("TRACK456", result.getTrackingNumber());
	        assertEquals("FedEx", result.getShoppingProvider());
	        assertEquals(LocalDate.now().plusDays(5), result.getEstimatedDeliveyDate());
	        assertEquals(LocalDate.now().plusDays(4), result.getActualDeliveryDate());
	        assertTrue(result.isDelivered());
	    }

	    @Test
	    void testDeleteData() throws DeliveryNotFoundException {
	        Delivery delivery = new Delivery();
	        delivery.setDeliveryId("delivery-123");

	        when(deliveryRepository.findById("delivery-123")).thenReturn(Optional.of(delivery));

	        deliveryService.deletedata("delivery-123");

	        verify(deliveryRepository, times(1)).deleteById("delivery-123");
	    }

	    @Test
	    void testGetDeliveryByStatus() {
	        List<Delivery> deliveries = new ArrayList<>();
	        
	        // Create deliveries with different statuses
	        Delivery delivery1 = new Delivery();
	        delivery1.setDeliveryStatus(DeliveryStatus.PENDING);
	        
	        Delivery delivery2 = new Delivery();
	        delivery2.setDeliveryStatus(DeliveryStatus.IN_TRANSI);
	        
	        // Add both deliveries to the list
	        deliveries.add(delivery1);
	        deliveries.add(delivery2);

	        // Mock the repository to return the list of deliveries
	        when(deliveryRepository.findByDeliveryStatus(DeliveryStatus.PENDING)).thenReturn(
	            List.of(delivery1) // Only return the delivery with PENDING status
	        );

	        // Call the service method
	        List<Delivery> result = deliveryService.getDeliveryByStatus(DeliveryStatus.PENDING);

	        // Assert that the result contains only the delivery with PENDING status
	        assertEquals(1, result.size());
	        assertEquals(DeliveryStatus.PENDING, result.get(0).getDeliveryStatus());
	    }


}
