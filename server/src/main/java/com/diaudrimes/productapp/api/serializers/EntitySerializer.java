package com.diaudrimes.productapp.api.serializers;

import java.lang.reflect.Type;

import com.diaudrimes.productapp.services.AbstractService;
import com.diaudrimes.productapp.services.LogService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public abstract class EntitySerializer<T> extends AbstractService {

	public EntitySerializer(LogService logService) {
		super(logService);
	}

	public abstract JsonElement serialize(T entity, Type type);
	public abstract T deserialize(JsonElement jsonElement, Type type) throws JsonParseException;
}
