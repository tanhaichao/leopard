package io.leopard.json.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

public class VOSerializerProvider extends DefaultSerializerProvider {

	private static final long serialVersionUID = 1L;

	public VOSerializerProvider() {
		super();
	}

	protected VOSerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
		super(src, config, f);
	}

	public void serializeValue(JsonGenerator gen, Object value) throws IOException {
		System.out.println("serializeValue:" + value);
		super.serializeValue(gen, value);

	}

	@Override
	public VOSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
		return new VOSerializerProvider(this, config, jsf);
	}
}
