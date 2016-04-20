package io.leopard.web.mvc;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ImageSerializer extends AbstractJsonSerializer<String> {

	@Override
	public void serialize(String uri, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		String url = this.serialize(uri);
		jgen.writeString(url);
	}

	@Override
	public String serialize(String uri) {
		return ImageUrlConverterImpl.getInstance().convert(uri);
	}

}
