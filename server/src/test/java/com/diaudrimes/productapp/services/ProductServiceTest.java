package com.diaudrimes.productapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.diaudrimes.productapp.api.exceptions.ResourceNotFoundException;
import com.diaudrimes.productapp.persistence.entities.Product;
import com.diaudrimes.productapp.persistence.entities.validators.ProductValidator;
import com.diaudrimes.productapp.persistence.entities.validators.exceptions.EntityValidationException;
import com.diaudrimes.productapp.persistence.repositories.ProductRepository;

/**
 * Class used to test the service layer of Products
 *
 * @author diego
 *
 */
public class ProductServiceTest {

	private ProductService productService;
	private ProductRepository productRepository;
	private ProductValidator productValidator;

	private final Long existingId = 1L;
	private final Long nonExistingId = 10000000L;

	@BeforeEach
	public void beforeEach() {
		this.productRepository = Mockito.mock(ProductRepository.class);
		this.productValidator = Mockito.mock(ProductValidator.class);

		LogService logService = mock(LogService.class);

		this.productService = new ProductService(productRepository, productValidator, logService);
	}

	@Test
	@DisplayName("GIVEN a valid product ID  "
			+ "WHEN the user search for an existing product "
			+ "THEN product is returned successfully.")
	public void test_getProductById_success() throws Exception {
		Product p = new Product();
		Optional<Product> opt = Optional.of(p);
		when(this.productRepository.findById(anyLong())).thenReturn(opt);

		Product existingProduct = this.productService.findById(existingId);
		assertEquals(p, existingProduct);
	}

	@Test
	@DisplayName("GIVEN an invalid product ID  " + "WHEN the user search for a product "
			+ "THEN ResourceNotFoundException is thrown.")
	public void test_getProductById_NotFound() throws Exception {
		Product p = new Product();
		Optional<Product> opt = Optional.of(p);
		when(this.productRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> {
			this.productService.findById(nonExistingId);
		});
	}

	@Test
	@DisplayName("GIVEN valid parameters " + "WHEN the user creates a product "
			+ "THEN created product is returned successfully.")
	public void test_createProduct_success() throws Exception {
		Product p = new Product();
		p.setName("Test 1");
		p.setQuantity(1l);
		when(this.productRepository.save(any())).thenReturn(p);

		Product createdProduct = this.productService.create("Test 1", 1l);
		assertEquals(p, createdProduct);
	}

	@Test
	@DisplayName("GIVEN an invalid name " + "WHEN the user creates a new product "
			+ "THEN EntityValidationException is thrown.")
	public void test_createProduct_InvalidNameParameter() throws Exception {
		when(this.productRepository.save(any())).thenThrow(EntityValidationException.class);

		assertThrows(EntityValidationException.class, () -> {
			this.productService.create("  ", 1l);
		});
	}

	@Test
	@DisplayName("GIVEN an invalid quantity " + "WHEN the user creates a new product "
			+ "THEN EntityValidationException is thrown.")
	public void test_createProduct_InvalidQuantityParameter() throws Exception {
		when(this.productRepository.save(any())).thenThrow(EntityValidationException.class);

		assertThrows(EntityValidationException.class, () -> {
			this.productService.create("Test 23", -1l);
		});
	}

	@Test
	@DisplayName("GIVEN valid parameters " + "WHEN the user updates a product "
			+ "THEN updated product is returned successfully.")
	public void test_updateProduct_success() throws Exception {
		Product p = new Product();
		p.setModifiedOn(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("Z")));

		Product oldObj = new Product();
		oldObj.setModifiedOn(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("Z")));

		when(this.productRepository.save(any())).thenReturn(p);

		Product updatedProduct = this.productService.update(p);
		assertEquals(oldObj.getId(), updatedProduct.getId());
		assertNotEquals(oldObj.getModifiedOn(), updatedProduct.getModifiedOn());
	}

	@Test
	@DisplayName("GIVEN invalid parameters " + "WHEN the user updates a product "
			+ "THEN EntityValidationException is thrown.")
	public void test_updateProduct_EntityValidationException() throws Exception {
		Product p = new Product();
		p.setName("  ");
		p.setQuantity(-1l);
		p.setModifiedOn(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("Z")));

		when(this.productRepository.save(any())).thenThrow(EntityValidationException.class);

		assertThrows(EntityValidationException.class, () -> {
			this.productService.update(p);
		});
	}

	@Test
	@DisplayName("GIVEN valid parameters " + "WHEN the user deletes a product "
			+ "THEN product is deleted successfully.")
	public void test_deleteProduct_success() throws Exception {
		Boolean isDeleted = this.productService.delete(existingId);
		assertTrue(isDeleted);
	}

	@Test
	@DisplayName("GIVEN invalid parameters " + "WHEN the user updates a product "
			+ "THEN EntityValidationException is thrown.")
	public void test_deleteProduct_ResourceNotFoundException() throws Exception {
		doThrow(ResourceNotFoundException.class).when(this.productRepository).deleteById(anyLong());

		assertThrows(ResourceNotFoundException.class, () -> {
			this.productService.delete(nonExistingId);
		});
	}
}
