package com.deliveryservices.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.deliveryservices.entity.Delivery;
import com.deliveryservices.entity.DeliveryStatus;
import com.deliveryservices.entity.Order;
import com.deliveryservices.exception.DeliveryNotFoundException;
import com.deliveryservices.externalServices.OrderServices;
import com.deliveryservices.repository.DeliveryRepository;

@Service(value = "deliveryService")
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Autowired
	private OrderServices orderServices;

//	@Autowired
//	private RestTemplate restTemplate;

	@Override
	public Delivery createdelivery(Delivery delivery) {
		String randomId = UUID.randomUUID().toString();
		// fetch order details using feign client
		Order order = orderServices.getOrderById(delivery.getOrderId());

		if (order != null) {
			delivery.setAddress(order.getShippingAddress());
//			delivery.setDeliveryStatus(DeliveryStatus.PENDING);

			// Set the delivery status based on the order status
			switch (order.getOrderStatus()) {
			case "SHIPPED":
				delivery.setDeliveryStatus(DeliveryStatus.IN_TRANSI);
				break;
			case "DELIVERED":
				delivery.setDeliveryStatus(DeliveryStatus.DELIVERED);
				break;
			default:
				delivery.setDeliveryStatus(DeliveryStatus.PENDING);
				break;
			}
			
			if (delivery.getEstimatedDeliveyDate() == null) {
				delivery.setEstimatedDeliveyDate(LocalDate.now().plusDays(3));
			}
			
			if(delivery.getActualDeliveryDate()==null) {
				delivery.setActualDeliveryDate(LocalDate.now().plusDays(2));
			}

		}
		delivery.setDeliveryId(randomId);
		Delivery d = deliveryRepository.save(delivery);
		return d;

	}

	@Override
	public List<Delivery> getAllData() {
		// TODO Auto-generated method stub
		return deliveryRepository.findAll();
	}

	@Override
	public Delivery getdeliveryByOrderId(String deliveryId) throws DeliveryNotFoundException {
		// TODO Auto-generated method stub
		return deliveryRepository.findById(deliveryId)
				.orElseThrow(() -> new DeliveryNotFoundException("Delivery not found"));
	}

	@Override
	public Delivery updatedata(String deliveryId, Delivery delivery) throws DeliveryNotFoundException {
	    Optional<Delivery> optional = deliveryRepository.findById(deliveryId);
	    if (optional.isPresent()) {
	        Delivery existingDelivery = optional.get();

	        // Update fields only if they are not null in the provided delivery object
	        if (delivery.getOrderId() != null) {
	            existingDelivery.setOrderId(delivery.getOrderId());
	        }
	        if (delivery.getAddress() != null) {
	            existingDelivery.setAddress(delivery.getAddress());
	        }
	        if (delivery.getTrackingNumber() != null) {
	            existingDelivery.setTrackingNumber(delivery.getTrackingNumber());
	        }
	        if (delivery.getShoppingProvider() != null) {
	            existingDelivery.setShoppingProvider(delivery.getShoppingProvider());
	        }
	        if (delivery.getEstimatedDeliveyDate() != null) {
	            existingDelivery.setEstimatedDeliveyDate(delivery.getEstimatedDeliveyDate());
	        }
	        if (delivery.getActualDeliveryDate() != null) {
	            existingDelivery.setActualDeliveryDate(delivery.getActualDeliveryDate());
	        }

	        existingDelivery.setDelivered(delivery.isDelivered()); // Update boolean field directly

	        // Save the updated delivery back to the repository
	        return deliveryRepository.save(existingDelivery);
	    } else {
	        throw new DeliveryNotFoundException("Not found with given id: " + deliveryId);
	    }
	}
	

	@Override
	public void deletedata(String deliveryId) throws DeliveryNotFoundException {
		// TODO Auto-generated method stub
		Optional<Delivery> optional = Optional.ofNullable(deliveryRepository.findById(deliveryId)
				.orElseThrow(() -> new DeliveryNotFoundException("Given details are not found")));
		deliveryRepository.deleteById(deliveryId);

	}

	@Override
	public List<Delivery> getDeliveryByStatus(DeliveryStatus deliveryStatus) {
		// TODO Auto-generated method stub
		return deliveryRepository.findByDeliveryStatus(deliveryStatus);
	}

}
