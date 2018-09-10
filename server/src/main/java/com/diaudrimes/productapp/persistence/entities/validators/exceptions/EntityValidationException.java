package com.diaudrimes.productapp.persistence.entities.validators.exceptions;

import java.util.ArrayList;
import java.util.List;

public class EntityValidationException extends Exception {

	private final Class entityClass;
	private final List<String> validationMessages;

	public EntityValidationException(Class entityClass, String message) {
		super("Bad Request. Failed to validate the entity");

		this.entityClass = entityClass;
		this.validationMessages = new ArrayList<String>();
		this.validationMessages.add(message);
	}

	public EntityValidationException(Class entityClass, List<String> messages) {
		super("Bad Request. Failed to validate the entity");

		this.entityClass = entityClass;
		this.validationMessages = messages;
	}

	public Class getEntityClass() {
		return this.entityClass;
	}

	public List<String> getValidationMessages() {
		return this.validationMessages;
	}
}
