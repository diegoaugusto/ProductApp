package com.diaudrimes.productapp.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.diaudrimes.productapp.persistence.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
