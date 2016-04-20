package io.leopard.web.mvc;

import com.fasterxml.jackson.databind.JsonSerializer;

public abstract class AbstractJsonSerializer<T> extends JsonSerializer<T> {

	public abstract T serialize(T value);
}
