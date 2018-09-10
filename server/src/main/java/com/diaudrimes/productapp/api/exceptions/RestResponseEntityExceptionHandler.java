package com.diaudrimes.productapp.api.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.diaudrimes.productapp.api.dto.ErrorDTO;
import com.diaudrimes.productapp.persistence.entities.validators.exceptions.EntityValidationException;
import com.diaudrimes.productapp.services.LogService;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends BaseResponseEntityExceptionHandler {

	@Autowired
	public RestResponseEntityExceptionHandler(LogService logService) {
		super(logService);
	}

	@ExceptionHandler(value = {EntityValidationException.class})
	public ResponseEntity<Object> handleEntityValidationException(EntityValidationException ex, WebRequest request) {
		StringBuilder errorMessage = new StringBuilder(ex.getMessage());

		if (ex.getValidationMessages() != null && ex.getValidationMessages().size() > 0) {
			errorMessage.append("\n");
			for (String m : ex.getValidationMessages()) {
				errorMessage.append("\n").append(m);
			}
		}

		this.logService.error(RestResponseEntityExceptionHandler.class, errorMessage.toString(), ex);

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrorDTO error = new ErrorDTO(status.value(), errorMessage.toString());
		return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

}
