package com.diaudrimes.productapp.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.diaudrimes.productapp.api.exceptions.ResourceNotFoundException;
import com.diaudrimes.productapp.api.serializers.ProductSerializer;
import com.diaudrimes.productapp.persistence.entities.Product;
import com.diaudrimes.productapp.persistence.entities.validators.exceptions.EntityValidationException;
import com.diaudrimes.productapp.services.LogService;
import com.diaudrimes.productapp.services.ProductService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Main class for REST services for Products.
 *
 * @author diego
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController extends ResourceController {

  private final ProductService productService;

  @Autowired
  public ProductController(LogService logService, ProductService productService) {
    super(logService);
    this.productService = productService;
  }



  @Autowired
  private ProductSerializer serializer;;

  /**
   * Get a list of all Products available in the system.
   *
   * @return All products in the system.
   */
  @CrossOrigin
  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody Iterable<Product> getAllProducts() {
    this.getLogService().info(this.getClass(), "Returning all Products.");
    Iterable<Product> result = this.productService.findAll();
    return result;
  }

  /**
   * Get one {@link Product} based on its product id.
   *
   * @param productId
   * @return
   */
  @CrossOrigin
  @GetMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody Product getProductById(@PathVariable Long productId) {
    // This returns a JSON or XML with the products
    this.getLogService().info(this.getClass(), "Returning Product with id ["+ productId +"].");
    Product product = this.productService.findById(productId);

    if (product == null) {
      throw new ResourceNotFoundException("Product with ID [ "+ productId +" ] has not been found or does not exist.");
    }

    return product;
  }

  /**
   * Creates a new product.
   *
   * @param productEntity
   * @return
   * @throws EntityValidationException
   */
  @CrossOrigin
  @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody Product addNewProduct(@RequestBody String productEntity) throws EntityValidationException {
    JsonObject requestBody = (JsonObject) new JsonParser().parse(productEntity);
    ProductSerializer serializer = new ProductSerializer(logService);
    Product p = serializer.deserialize(requestBody, Product.class);

    this.getLogService().info(this.getClass(), "Creating a new Product.");
    Product createdProduct = this.productService.create(p.getName(), p.getQuantity());
    return createdProduct;
  }

  /**
   * Updates an existing product.
   *
   * @param productId
   * @param productEntity
   * @return
   * @throws EntityValidationException
   */
  @CrossOrigin
  @PatchMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody Product updateProduct(@PathVariable Long productId,
      @RequestBody String productEntity) throws EntityValidationException {
    JsonObject requestBody = (JsonObject) new JsonParser().parse(productEntity);
    ProductSerializer serializer = new ProductSerializer(logService);
    Product product = serializer.deserialize(requestBody, Product.class);
    product.setId(productId);

    Product createdProduct = null;
    this.getLogService().info(this.getClass(),
        "Updating an existing Product of id [" + productId + "].");
    createdProduct = this.productService.update(product);
    return createdProduct;
  }

  /**
   * Deletes a product.
   *
   * @param productId
   * @return
   */
  @CrossOrigin
  @DeleteMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody Boolean deleteProduct(@PathVariable Long productId) {
    this.getLogService().info(this.getClass(), "Deleting Product of id [" + productId + "].");
    boolean isProductDeleted = this.productService.delete(productId);
    return isProductDeleted;
  }

}
