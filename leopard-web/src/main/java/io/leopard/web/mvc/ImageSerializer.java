package io.leopard.web.mvc;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ImageSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String uri, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		String url = ImageUrlConverterImpl.getInstance().convert(uri);
		jgen.writeString(url);
	}

}
