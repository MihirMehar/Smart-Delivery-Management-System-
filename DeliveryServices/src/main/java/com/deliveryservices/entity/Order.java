package com.deliveryservices.entity;

import java.time.LocalDate;

public class Order {

	private String orderId;
	private String customerId;
	private String productDetails;
	private double totalAmmount;
	private String orderStatus;
	private String shippingAddress;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}
	public double getTotalAmmount() {
		return totalAmmount;
	}
	public void setTotalAmmount(double totalAmmount) {
		this.totalAmmount = totalAmmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDate getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Order(String orderId, String customerId, String productDetails, double totalAmmount, String orderStatus,
			String shippingAddress, LocalDate createdAt, LocalDate updatedAt) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.productDetails = productDetails;
		this.totalAmmount = totalAmmount;
		this.orderStatus = orderStatus;
		this.shippingAddress = shippingAddress;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Order() {
		
	}
}
