package com.diaudrimes.productapp.services;

/**
 * Provides common functionality in all subclasses
 *
 * @author diego
 *
 */
public class AbstractService {

	private final LogService logService;
	public AbstractService(LogService logService) {
		this.logService = logService;
	}

	public LogService getLogService() {
		return this.logService;
	}

}
