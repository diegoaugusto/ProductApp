package com.diaudrimes.productapp.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.diaudrimes.productapp.services.LogService;

/**
 * Base class for service controllers used to implement the REST API.
 *
 * @author diego
 *
 */
public abstract class ResourceController {

  public ResourceController(@Autowired LogService logService) {
    this.logService = logService;
  }

  LogService logService;

  /**
   * Get the instance of the {@link LogService}
   * @return an instance of the {@link LogService}
   */
  protected LogService getLogService() {
    return this.logService;
  }
}
