package com.diaudrimes.productapp.persistence.entities.validators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diaudrimes.productapp.persistence.entities.Product;
import com.diaudrimes.productapp.persistence.entities.validators.exceptions.EntityValidationException;

/**
 * Class that validates the {@link Product} entity.
 *
 * @author diego
 *
 */
@Component
@Scope(value = "singleton")
public class ProductValidator implements EntityValidator<Product, Long> {

	/**
	 * Enumeration containing all the validation messages that will be returned to the user in case of wrong values.
	 *
	 * @author diego
	 *
	 */
	public enum PredefinedMessages {
		EntityIsRequired("Product cannot be null or empty."),
		IdShouldBeValid("Product ID should be valid and cannot be 0 or negative."),
		NameIsRequired("Product name is required and cannot be empty."),
		QuantityIsRequired("The quantity of product is required and cannot be negative.");

		private PredefinedMessages(String message) {
			this.message = message;
		}

		private final String message;
		public String getMessage() {
			return this.message;
		}
	}

	/**
	 * Method used to validate a product.
	 *
	 * More complex verifications can be made here.
	 */
	@Override
	public List<String> validate(Product p)
			throws EntityValidationException {
		List<String> result = new ArrayList<String>();

		if (p == null) {
			result.add(PredefinedMessages.EntityIsRequired.getMessage());
		} else {
			// In case of updates, the ID should be valid and greater than 0
			if (p.getId() != null && p.getId() < 1) {
				result.add(PredefinedMessages.IdShouldBeValid.getMessage());
			}

			// Name cannot be null or empty
			if (p.getName() == null || "".equals(p.getName().trim())) {
				result.add(PredefinedMessages.NameIsRequired.getMessage());
			}

			// Quantity cannot be null and must be a positive number
			if (p.getQuantity() == null || p.getQuantity() < 0) {
				result.add(PredefinedMessages.QuantityIsRequired.getMessage());
			}
		}

		return result.size() > 0 ? result : null;
	}
}
