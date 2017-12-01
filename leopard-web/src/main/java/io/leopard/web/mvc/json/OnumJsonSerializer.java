package io.leopard.web.mvc.json;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.leopard.lang.inum.JsonField;
import io.leopard.lang.inum.Onum;

/**
 * Onum Json序列化.
 * 
 * @author 谭海潮
 *
 */
public class OnumJsonSerializer extends StdSerializer<Onum<?, ?>> {

	private static final long serialVersionUID = 1L;

	public OnumJsonSerializer() {
		super(Onum.class, false);
	}

	@Override
	public void serialize(Onum<?, ?> onum, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("key");
		generator.writeObject(onum.getKey());
		generator.writeFieldName("desc");
		generator.writeObject(onum.getDesc());
		
		List<Field> fieldList = this.listJsonField(onum);
		if (!fieldList.isEmpty()) {
			for (Field field : fieldList) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Object value;
				try {
					value = field.get(onum);
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				generator.writeFieldName(fieldName);
				generator.writeObject(value);
			}
		}
		
		generator.writeEndObject();
	}

	protected List<Field> listJsonField(Onum<?, ?> onum) {
		// TODO 缓存
		List<Field> fieldList = new ArrayList<Field>();
		Class<?> clazz = onum.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			JsonField anno = field.getAnnotation(JsonField.class);
			if (anno == null) {
				continue;
			}
			fieldList.add(field);
		}
		return fieldList;
	}

}
