package com.diaudrimes.productapp.persistence.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.diaudrimes.productapp.persistence.entities.Product;

/**
 * Class used to test the operations in the {@link ProductRepository}
 *
 * @author diego
 *
 */
@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@BeforeEach
	public void beforeEach() {
		this.productRepository = Mockito.mock(ProductRepository.class);
	}

	@Test
	@DisplayName("GIVEN a database containing products " + "WHEN a product is searched by ID "
			+ "AND there is not a product with that ID in the database " + "THEN the repository should return null ")
	public void test_getAllProducts_success() {
		this.productRepository.save(new Product("TEst 1", 2l));

		Iterable<Product> list = this.productRepository.findAll();
		for (Product p : list) {
			System.out.println(p.getId());
		}
		// when(this.productRepository.findById(any())).then

		// assetThrows();
	}
}
