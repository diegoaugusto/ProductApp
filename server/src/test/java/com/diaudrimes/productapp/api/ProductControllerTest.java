package com.diaudrimes.productapp.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.diaudrimes.productapp.api.controllers.ProductController;
import com.diaudrimes.productapp.api.exceptions.ResourceNotFoundException;
import com.diaudrimes.productapp.persistence.entities.Product;
import com.diaudrimes.productapp.persistence.entities.validators.exceptions.EntityValidationException;
import com.diaudrimes.productapp.services.LogService;
import com.diaudrimes.productapp.services.ProductService;

public class ProductControllerTest {

  private ProductService productService;
  private ProductController controller;

  private final Long existingId = 1L;
  private final Long nonExistingId = 10000000L;

  @BeforeEach
  public void beforeEach() {
    this.productService = mock(ProductService.class);
    LogService logService = mock(LogService.class);

    this.controller = new ProductController(logService, productService);
  }

  // ##### GET /products
  @Test
  @DisplayName("GIVEN that service GET /products is available "
      + "WHEN a request is made to get all products "
      + "THEN it is returned with HTTP 200 OK code.")
  public void test_getAllProducts_Found() throws Exception {
    Iterable<Product> products = new ArrayList<Product>();

    when(this.productService.findAll()).thenReturn(products);
    Iterable<Product> listOfProductsFromController = this.controller.getAllProducts();

    assertNotNull("Expected list of products.", listOfProductsFromController);
  }

  // ##### GET /products/{productId}
  @Test
  @DisplayName("GIVEN that service GET /products/{producId} is available "
      + "WHEN a request is made to get a product " + "AND the product with id {productId} exists "
      + "THEN it is returned successfully.")
  public void test_getProductById_Found() throws Exception {
    Product product = new Product();

    when(this.productService.findById(anyLong())).thenReturn(product);
    Product p = this.controller.getProductById(existingId);

    assertNotNull("Expected product.", p);
  }

  @Test
  @DisplayName("GIVEN that service GET /products/{producId} is available "
      + "WHEN a request is made to get a product "
      + "AND the product with id {productId} does not exist "
      + "THEN exception ResourceNotFoundException is thrown.")
  public void test_getProductById_NotFound() throws Exception {
    when(this.productService.findById(anyLong())).thenReturn(null);

    assertThrows(ResourceNotFoundException.class, () -> {
      this.controller.getProductById(nonExistingId);
    });
  }

  @Test
  @DisplayName("GIVEN that service GET /products/{producId} is available "
      + "WHEN a request is made to get a product " + "AND the id {productId} is invalid "
      + "THEN exception ResourceNotFoundException is thrown.")
  public void test_getProductById_InvalidId() throws Exception {
    when(this.productService.findById(anyLong())).thenReturn(null);

    assertThrows(ResourceNotFoundException.class, () -> {
      this.controller.getProductById(-1L);
    });
  }

  // ##### POST /products
  @Test
  @DisplayName("GIVEN that service POST /products is available "
      + "WHEN a request is made to create a product " + "AND the parameters are valid "
      + "THEN Product is successfully created.")
  public void test_postProduct_ValidParameters() throws Exception {
    Product p = new Product();
    p.setId(1l);
    p.setName("Test 1");
    p.setQuantity(1l);

    when(this.productService.create(anyString(), anyLong())).thenReturn(p);

    String productEntity = "{\"name\":\"Test 1\",\"quantity\":1}";
    Product product = this.controller.addNewProduct(productEntity);
    assertEquals(p.getId(), product.getId());
    assertEquals(p.getName(), product.getName());
    assertEquals(p.getQuantity(), product.getQuantity());
    assertNotNull(product.getCreatedOn());
    assertNotNull(product.getModifiedOn());
  }

  @Test
  @DisplayName("GIVEN that service POST /products is available "
      + "WHEN a request is made to create a product " + "AND the name is invalid "
      + "THEN EntityValidationException is thrown.")
  public void test_postProduct_InvalidName() throws Exception {
    when(this.productService.create(anyString(), anyLong()))
    .thenThrow(EntityValidationException.class);

    assertThrows(EntityValidationException.class, () -> {
      String productEntity = "{\"name\":\"   \",\"quantity\":1}";
      this.controller.addNewProduct(productEntity);
    });
  }

  @Test
  @DisplayName("GIVEN that service POST /products is available "
      + "WHEN a request is made to create a product " + "AND the quantity is invalid "
      + "THEN EntityValidationException is thrown.")
  public void test_postProduct_InvalidQuantity() throws Exception {
    when(this.productService.create(anyString(), anyLong()))
    .thenThrow(EntityValidationException.class);

    assertThrows(EntityValidationException.class, () -> {
      String productEntity = "{\"name\":\"Test 1\",\"quantity\":-1}";
      this.controller.addNewProduct(productEntity);
    });
  }

  @Test
  @DisplayName("GIVEN that service POST /products is available "
      + "WHEN a request is made to create a product " + "AND the name and quantity are invalid "
      + "THEN EntityValidationException is thrown.")
  public void test_postProduct_InvalidNameAndQuantity() throws Exception {
    when(this.productService.create(anyString(), anyLong()))
    .thenThrow(EntityValidationException.class);

    assertThrows(EntityValidationException.class, () -> {
      String productEntity = "{\"name\":\"   \",\"quantity\":-1}";
      this.controller.addNewProduct(productEntity);
    });
  }

  // ##### PATCH /products/{productId}
  @Test
  @DisplayName("GIVEN that service PATCH /products/{productId} is available "
      + "WHEN a request is made to udpate a product "
      + "AND the product with {productId} exists in the database "
      + "THEN the Product with ID {productId} is successfully updated.")
  public void test_patchProduct_ValidParameters() throws Exception {
    Product p = new Product();
    p.setId(existingId);
    p.setName("Test 1 - UPDATED");
    p.setQuantity(4l);
    p.setCreatedOn(ZonedDateTime.of(2018, 9, 1, 0, 0, 0, 0, ZoneId.of("Z")));
    p.setModifiedOn(ZonedDateTime.now(ZoneId.of("Z")));

    when(this.productService.update(any())).thenReturn(p);

    String productEntity = "{\"name\":\"Test 1 - UPDATED\",\"quantity\":4}";
    Product product = this.controller.updateProduct(existingId, productEntity);
    assertEquals(p.getId(), product.getId());
    assertEquals(p.getName(), product.getName());
    assertEquals(p.getQuantity(), product.getQuantity());
    assertEquals(p.getCreatedOn(), product.getCreatedOn());
    assertEquals(p.getModifiedOn(), product.getModifiedOn());
  }

  @Test
  @DisplayName("GIVEN that service PATCH /products/{productId} is available "
      + "WHEN a request is made to update a product "
      + "AND the product with {productId} ID exists in the database "
      + "AND the name is invalid " + "THEN EntityValidationException is thrown.")
  public void test_patchProduct_InvalidName() throws Exception {
    when(this.productService.update(any())).thenThrow(EntityValidationException.class);

    assertThrows(EntityValidationException.class, () -> {
      String productEntity = "{\"name\":\"   \",\"quantity\":1}";
      this.controller.updateProduct(existingId, productEntity);
    });
  }

  @Test
  @DisplayName("GIVEN that service PATCH /products/{productId} is available "
      + "WHEN a request is made to update a product "
      + "AND the product with {productId} ID exists in the database "
      + "AND the quantity is invalid " + "THEN EntityValidationException is thrown.")
  public void test_patchProduct_InvalidQuantity() throws Exception {
    when(this.productService.update(any())).thenThrow(EntityValidationException.class);

    assertThrows(EntityValidationException.class, () -> {
      String productEntity = "{\"name\":\"Test 1\",\"quantity\":-1}";
      this.controller.updateProduct(existingId, productEntity);
    });
  }

  @Test
  @DisplayName("GIVEN that service PATCH /products/{productId} is available "
      + "WHEN a request is made to update a product "
      + "AND the product with {productId} ID exists in the database "
      + "AND the name and quantity are invalid " + "THEN EntityValidationException is thrown.")
  public void test_patchProduct_InvalidNameAndQuantity() throws Exception {
    when(this.productService.update(any())).thenThrow(EntityValidationException.class);

    assertThrows(EntityValidationException.class, () -> {
      String productEntity = "{\"name\":\"   \",\"quantity\":-1}";
      this.controller.updateProduct(existingId, productEntity);
    });
  }

  @Test
  @DisplayName("GIVEN that service PATCH /products/{productId} is available "
      + "WHEN a request is made to update a product "
      + "AND the product with {productId} ID does not exists in the database "
      + "AND the name and quantity are valid " + "THEN a new Product is created.")
  public void test_patchProduct_NonExistingId() throws Exception {
    Product p = new Product();
    p.setId(1l);
    p.setName("Test 1");
    p.setQuantity(1l);

    when(this.productService.update(any())).thenReturn(p);
    String productEntity = "{\"name\":\"Test 2 - UPDATED\",\"quantity\":4}";
    Product product = this.controller.updateProduct(nonExistingId, productEntity);

    assertEquals(p.getId(), product.getId());
    assertEquals(p.getName(), product.getName());
    assertEquals(p.getQuantity(), product.getQuantity());
  }


  // ##### DELETE /products/{productId}
  @Test
  @DisplayName("GIVEN that service DELETE /products/{productId} is available "
      + "WHEN a request is made to delete a product "
      + "AND the product with {productId} exists in the database "
      + "THEN the Product with ID {productId} is successfully deleted.")
  public void test_deleteProduct_ValidID() throws Exception {

    when(this.productService.delete(anyLong())).thenReturn(true);

    Boolean deleted = this.controller.deleteProduct(existingId);
    assertTrue("Product has been successfully deleted.", deleted);
  }

  @Test
  @DisplayName("GIVEN that service DELETE /products/{productId} is available "
      + "WHEN a request is made to delete a product "
      + "AND the product with {productId} does not exists in the database "
      + "THEN the Product with ID {productId} is successfully deleted.")
  public void test_deleteProduct_InvalidID() throws Exception {

    when(this.productService.delete(anyLong())).thenThrow(ResourceNotFoundException.class);

    assertThrows(ResourceNotFoundException.class, () -> {
      this.controller.deleteProduct(nonExistingId);
    });
  }
}
