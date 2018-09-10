package com.diaudrimes.productapp.api.exceptions;

/**
 * Exception thrown when a queried resource was not found
 * @author diego
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * Contructor
	 * @param message The message of the exception
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}

	/**
	 * Contructor
	 * @param message the message of the exception
	 * @param innerException Inner exception
	 */
	public ResourceNotFoundException(String message, Throwable innerException) {
		super(message, innerException);
	}
}
