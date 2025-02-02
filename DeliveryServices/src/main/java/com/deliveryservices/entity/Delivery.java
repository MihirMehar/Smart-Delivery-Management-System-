package com.deliveryservices.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Delivery {
	
	@Id
	private String deliveryId;
	private String orderId;
	private String Address;
	private DeliveryStatus deliveryStatus;
	
	private String trackingNumber;
	private String shoppingProvider;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate estimatedDeliveyDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate actualDeliveryDate;
	
	private boolean isDelivered;

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

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
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

	public Delivery(String deliveryId, String orderId, String address, DeliveryStatus deliveryStatus,
			String trackingNumber, String shoppingProvider, LocalDate estimatedDeliveyDate,
			LocalDate actualDeliveryDate, boolean isDelivered) {
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

	@Override
	public String toString() {
		return "DeliveyEntity [deliveryId=" + deliveryId + ", orderId=" + orderId + ", Address=" + Address
				+ ", deliveryStatus=" + deliveryStatus + ", trackingNumber=" + trackingNumber + ", shoppingProvider="
				+ shoppingProvider + ", estimatedDeliveyDate=" + estimatedDeliveyDate + ", actualDeliveryDate="
				+ actualDeliveryDate + ", isDelivered=" + isDelivered + "]";
	}
	
	
	
}
