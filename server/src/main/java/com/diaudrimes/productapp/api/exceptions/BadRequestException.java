package com.diaudrimes.productapp.api.exceptions;

/**
 * Exception thrown when there is something not correct in the API request.
 * @author diego
 *
 */
public class BadRequestException extends Exception {

	public BadRequestException(String message) {
		super(message);
	}
	
	public BadRequestException(String message, Throwable innerException) {
		super(message, innerException);
	}
}
