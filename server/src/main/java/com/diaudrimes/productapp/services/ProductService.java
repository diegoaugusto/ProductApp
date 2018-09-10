package com.diaudrimes.productapp.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.diaudrimes.productapp.api.exceptions.ResourceNotFoundException;
import com.diaudrimes.productapp.persistence.entities.Product;
import com.diaudrimes.productapp.persistence.entities.validators.ProductValidator;
import com.diaudrimes.productapp.persistence.entities.validators.exceptions.EntityValidationException;
import com.diaudrimes.productapp.persistence.repositories.ProductRepository;

/**
 * Service class responsible to perform validation tasks and execute any business logic
 * on {@link Product} objects before interacting with the Repository layer.
 *
 * @author diego
 *
 */
@Service
public class ProductService extends AbstractService {

	private final ProductRepository productRepository;

	private final ProductValidator validator;

	public ProductService(ProductRepository productRepository, ProductValidator productValidator, LogService logService) {
		super(logService);

		this.productRepository = productRepository;
		this.validator = productValidator;
	}

	/**
	 * Method that gets all the Products available in the system.
	 *
	 * @return An {@link Iterable} object containing the Products available in the system.
	 */
	public Iterable<Product> findAll() {
		return this.productRepository.findAll();
	}

	/**
	 * Method that gets one {@link Product} available in the system by its id.
	 *
	 * @param productId The ID of the product that is requested
	 * @return A {@link Product} object available in the system.
	 */
	public Product findById(Long productId) {
		try {
			Optional<Product> optProduct = this.productRepository.findById(productId);
			return optProduct.get();
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException("Product with ID [ "+ productId +" ] has not been found.", e);
		}
	}

	/**
	 * Method responsible to create a {@link Product} entity in the system.
	 *
	 * @param name Name of the product that cannot be empty
	 * @param quantity Quantity available of this product. It cannot be a negative value.
	 * @return An instance of the newly {@link Product} created.
	 * @throws EntityValidationException It is thrown when the validation of the name and quantity attributes returns error.
	 */
	public Product create(String name, Long quantity) throws EntityValidationException {
		Product product = new Product();
		product.setName(name);
		product.setQuantity(quantity);

		List<String> validationMessages = validator.validate(product);
		if (validationMessages != null && validationMessages.size() > 0) {
			throw new EntityValidationException(Product.class, validationMessages);
		}

		return this.productRepository.save(product);
	}

	/**
	 * Method responsible to update an existing {@link Product} entity in the system.
	 *
	 * @param product Existing {@link Product} in the system
	 * @return The updated {@link Product} entity
	 * @throws EntityValidationException It is thrown when the validation of the id, name and quantity attributes returns error.
	 */
	public Product update(Product product) throws EntityValidationException {
		List<String> validationMessages = validator.validate(product);
		if (validationMessages != null && validationMessages.size() > 0) {
			throw new EntityValidationException(Product.class, validationMessages);
		}

		product.setModifiedOn(ZonedDateTime.now(ZoneId.of("Z")));

		return this.productRepository.save(product);
	}

	/**
	 * Method responsible to delete an existing {@link Product} entity based on its ID.
	 *
	 * @param productId ID of the {@link Product} entity
	 * @return
	 */
	public boolean delete(Long productId) {
		try {
			this.productRepository.deleteById(productId);
			return true;
		} catch (NoSuchElementException | EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Product with ID [ "+ productId +" ] has not been found.", e);
		}
	}
}
