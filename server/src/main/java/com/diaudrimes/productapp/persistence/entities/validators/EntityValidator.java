package com.diaudrimes.productapp.persistence.entities.validators;

import java.util.List;
import com.diaudrimes.productapp.persistence.entities.Entity;
import com.diaudrimes.productapp.persistence.entities.validators.exceptions.EntityValidationException;

/**
 * Interface used for validators before persisting the objects
 *
 * @author diego
 *
 * @param <T> The type of the entity to be validated
 * @param <IDT> The type of the ID of the entity to be validated
 *
 */
public interface EntityValidator<T extends Entity<IDType>, IDType> {

  public List<String> validate(T entity)
      throws EntityValidationException;
}
