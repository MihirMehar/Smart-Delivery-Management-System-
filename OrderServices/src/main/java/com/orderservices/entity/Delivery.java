package com.orderservices.entity;



import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Id;

public class Delivery {
	
	
	private String deliveryId;
	private String orderId;
	private String Address;
	private String  deliveryStatus;
	
	private String trackingNumber;
	private String shoppingProvider;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate estimatedDeliveyDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate actualDeliveryDate;
	
	private boolean isDelivered= false;

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getShoppingProvider() {
		return shoppingProvider;
	}

	public void setShoppingProvider(String shoppingProvider) {
		this.shoppingProvider = shoppingProvider;
	}

	public LocalDate getEstimatedDeliveyDate() {
		return estimatedDeliveyDate;
	}

	public void setEstimatedDeliveyDate(LocalDate estimatedDeliveyDate) {
		this.estimatedDeliveyDate = estimatedDeliveyDate;
	}

	public LocalDate getActualDeliveryDate() {
		return actualDeliveryDate;
	}

	public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public Delivery(String deliveryId, String orderId, String address, String deliveryStatus, String trackingNumber,
			String shoppingProvider, LocalDate estimatedDeliveyDate, LocalDate actualDeliveryDate,
			boolean isDelivered) {
		super();
		this.deliveryId = deliveryId;
		this.orderId = orderId;
		Address = address;
		this.deliveryStatus = deliveryStatus;
		this.trackingNumber = trackingNumber;
		this.shoppingProvider = shoppingProvider;
		this.estimatedDeliveyDate = estimatedDeliveyDate;
		this.actualDeliveryDate = actualDeliveryDate;
		this.isDelivered = isDelivered;
	}

	public Delivery() {
		super();
	}
	
	
}


	
	
	
	