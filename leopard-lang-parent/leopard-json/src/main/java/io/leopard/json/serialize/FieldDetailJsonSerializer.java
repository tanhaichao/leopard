package io.leopard.json.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 属性详情
 * 
 * @author 谭海潮
 *
 */
public class FieldDetailJsonSerializer extends ContextualJsonSerializer<FieldDetail> {

	private FieldDetail anno;

	protected BeanProperty beanProperty;

	public FieldDetailJsonSerializer() {
	}

	public FieldDetailJsonSerializer(BeanProperty beanProperty, FieldDetail anno) {
		this.beanProperty = beanProperty;
		this.anno = anno;
	}

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		// gen.writeObject(value);
		// String fieldName = gen.getOutputContext().getCurrentName();
		// System.err.println("FieldDetailJsonSerializer serialize: className:" + className);
		try {
			Class<?> clazz;
			String className = anno.className();
			if (className.length() <= 0) {
				clazz = anno.using();
			}
			else {
				clazz = Class.forName(className);
			}
			@SuppressWarnings("unchecked")
			JsonSerializer<Object> serializer = (JsonSerializer<Object>) clazz.newInstance();
			serializer.serialize(value, gen, serializers);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		// System.err.println("JsonDetailJsonSerializer serialize value:" + value + " fieldName:" + fieldName + " type:" + beanProperty.getMember().getGenericType().getTypeName());
		// Object detail = null;
		// gen.writeObject(detail);
	}

}
