package io.leopard.web.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ImageListSerializer extends JsonSerializer<List<String>> {

	// ["/test/DSC_7348.jpg","/test/DSC_7352.jpg","/test/DSC_7353.jpg"]

	@Override
	public void serialize(List<String> list, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		List<String> urlList = null;
		if (list != null) {
			urlList = new ArrayList<String>();
			for (String uri : list) {
				String url = ImageUrlConverterImpl.getInstance().convert(uri);
				if (url != null) {
					urlList.add(url);
				}
			}
		}
		jgen.writeObject(urlList);
	}

}
