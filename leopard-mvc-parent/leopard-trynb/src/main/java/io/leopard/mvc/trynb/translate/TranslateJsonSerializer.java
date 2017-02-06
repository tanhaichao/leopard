package io.leopard.mvc.trynb.translate;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 翻译
 * 
 * @author 谭海潮
 *
 */
public class TranslateJsonSerializer extends JsonSerializer<String> {

	private static Translater translater = TranslaterImpl.getInstance();

	@Override
	public void serialize(String chinese, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		String english = translater.translate(chinese);
		if (english == null) {
			gen.writeString(chinese);
		}
		else {
			gen.writeString(english);
		}
	}

}
