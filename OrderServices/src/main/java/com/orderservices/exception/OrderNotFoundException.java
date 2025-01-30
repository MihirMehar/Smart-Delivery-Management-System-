package com.orderservices.exception;

public class OrderNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException() {
		super("Order not found on the server");
	}

	public OrderNotFoundException(String message) {
		super(message);
	}

}
