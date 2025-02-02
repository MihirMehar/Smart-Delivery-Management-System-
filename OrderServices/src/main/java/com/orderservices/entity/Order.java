package com.orderservices.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

	@Id
	private String orderId;

	@NotNull(message = "Customer Id cannot be null")
	private Long customerId;

	@NotBlank(message = "Product details can not be blank")
	private String productDetails;

	@Min(value = 1, message = "Total amount must be greater than zero")
	private Double totalAmount;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@NotBlank(message = "Shipping address can not be blank")
	private String shippingAddress;

	@CreatedDate
	@Column(updatable = false)
	private LocalDate createdAt;

	@LastModifiedDate
	private LocalDate updatedAt;

	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;

	@Transient
	private List<Delivery> delivery = new ArrayList<>(); // Ensure this is a List

	// Email field
	@Email
	private String customerEmail;

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	// Getters and Setters
	public List<Delivery> getDelivery() {
		return delivery;
	}

	public void setDelivery(List<Delivery> delivery) {
		this.delivery = delivery;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
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

	public Order(String orderId, @NotNull(message = "Customer Id cannot be null") Long customerId,
			@NotBlank(message = "Product details can not be blank") String productDetails,
			@Min(value = 1, message = "Total amount must be greater than zero") Double totalAmount,
			OrderStatus orderStatus, @NotBlank(message = "Shipping address can not be blank") String shippingAddress,
			LocalDate createdAt, LocalDate updatedAt, Boolean isDeleted, List<Delivery> delivery,
			@Email String customerEmail) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.productDetails = productDetails;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.shippingAddress = shippingAddress;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDeleted = isDeleted;
		this.delivery = delivery;
		this.customerEmail = customerEmail;
	}

	public Order() {

	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customerId=" + customerId + ", productDetails=" + productDetails
				+ ", totalAmount=" + totalAmount + ", orderStatus=" + orderStatus + ", shippingAddress="
				+ shippingAddress + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", isDeleted=" + isDeleted
				+ ", delivery=" + delivery + ", customerEmail=" + customerEmail + "]";
	}

}
