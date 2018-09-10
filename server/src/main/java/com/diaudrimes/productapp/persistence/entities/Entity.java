package com.diaudrimes.productapp.persistence.entities;

/**
 * Interface for all entity types.
 *
 * @author diego
 *
 */
public interface Entity<IDType> {

	/**
	 * Get the ID of the entity.
	 *
	 * @return {@link Object} with the entity's ID
	 */
	public IDType getId();

	/**
	 * Set the ID of the entity.
	 *
	 * @param id {@link Object} with the entity's ID
	 */
	public void setId(IDType id);
}
