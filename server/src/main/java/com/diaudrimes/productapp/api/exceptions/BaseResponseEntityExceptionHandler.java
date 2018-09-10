package com.diaudrimes.productapp.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.diaudrimes.productapp.api.dto.ErrorDTO;
import com.diaudrimes.productapp.persistence.entities.validators.exceptions.EntityValidationException;
import com.diaudrimes.productapp.services.LogService;

/**
 * Class used to handle exceptions in the REST services
 * @author diego
 *
 */
public class BaseResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	LogService logService;

	public BaseResponseEntityExceptionHandler(LogService logService) {
		this.logService = logService;
	}

	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleBasicException(Exception ex, WebRequest request) {
		String errorMessage = "Unexpected exception occured while processing this request.";
		this.logService.error(BaseResponseEntityExceptionHandler.class, errorMessage, ex);

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorDTO error = new ErrorDTO(status.value(), errorMessage);
		return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(value = {ResourceNotFoundException.class})
	public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
		String errorMessage = ex.getMessage();
		this.logService.error(BaseResponseEntityExceptionHandler.class, errorMessage, ex);

		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorDTO error = new ErrorDTO(status.value(), errorMessage);
		return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(value = {BadRequestException.class})
	public ResponseEntity<Object> handleBadRequestException(EntityValidationException ex, WebRequest request) {
		StringBuilder errorMessage = new StringBuilder(ex.getMessage());

		if (ex.getValidationMessages() != null && ex.getValidationMessages().size() > 0) {
			errorMessage.append("\n");
			for (String m : ex.getValidationMessages()) {
				errorMessage.append("\n").append(m);
			}
		}

		this.logService.error(BaseResponseEntityExceptionHandler.class, errorMessage.toString(), ex);

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrorDTO error = new ErrorDTO(status.value(), errorMessage.toString());
		return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
}
