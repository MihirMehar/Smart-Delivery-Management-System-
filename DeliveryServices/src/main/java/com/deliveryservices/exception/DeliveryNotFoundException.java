package com.deliveryservices.exception;

public class DeliveryNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeliveryNotFoundException() {
		super("Delivery with given details are not found");
	}
	
	public DeliveryNotFoundException(String message) {
		super(message);
	}
}
