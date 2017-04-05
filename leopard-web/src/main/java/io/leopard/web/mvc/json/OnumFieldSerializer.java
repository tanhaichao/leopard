package io.leopard.web.mvc.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 枚举序列化
 * 
 * @author 谭海潮
 *
 */
public class OnumFieldSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		gen.writeObject(value);
		// gen.g
		String fieldName = gen.getOutputContext().getCurrentName();

		// TODO ahai 获取不到field上的注解信息

		System.out.println("value:" + value + " fieldName:" + fieldName);
	}

}
