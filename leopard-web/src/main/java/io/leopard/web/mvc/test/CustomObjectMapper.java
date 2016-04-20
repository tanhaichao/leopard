package io.leopard.web.mvc.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;

public class CustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public CustomObjectMapper() {
		DefaultSerializerProvider sp = new DefaultSerializerProvider.Impl();
		sp.setNullValueSerializer(NullSerializer.instance);
		this.setSerializerProvider(sp);
	}

}
