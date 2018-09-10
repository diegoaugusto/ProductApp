package com.diaudrimes.productapp.persistence.entities;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@javax.persistence.Entity
public class Product implements Entity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Long quantity;

	private ZonedDateTime createdOn;

	private ZonedDateTime modifiedOn;

	// CONSTRUCTORS
	public Product() {
		this.createdOn = ZonedDateTime.now(ZoneId.of("Z"));
		this.modifiedOn = ZonedDateTime.now(ZoneId.of("Z"));
	}

	public Product(String name, Long quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	// GETTERS AND SETTERS
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public ZonedDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(ZonedDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public ZonedDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(ZonedDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
