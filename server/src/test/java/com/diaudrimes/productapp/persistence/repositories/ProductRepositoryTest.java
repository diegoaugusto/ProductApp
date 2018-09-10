package com.diaudrimes.productapp.persistence.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class used to test the operations in the {@link ProductRepository}
 *
 * @author diego
 *
 */
public class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @BeforeEach
  public void beforeEach() {
  }

  // @Test
  // @DisplayName("GIVEN a database containing products " + "WHEN a product is searched by ID "
  // + "AND there is not a product with that ID in the database "
  // + "THEN the repository should return null ")
  // public void test_getAllProducts_success() {
  // Iterable<Product> list = this.productRepository.findAll();
  // for (Product p : list) {
  // System.out.println(p.getId());
  // }
  // // when(this.productRepository.findById(any())).then
  //
  // // assetThrows();
  // }
}
