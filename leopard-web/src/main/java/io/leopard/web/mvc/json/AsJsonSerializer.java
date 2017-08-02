package io.leopard.web.mvc.json;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.web.mvc.AbstractJsonSerializer;

/**
 * 支持单个和列表数据输出
 * 
 * @author 谭海潮
 *
 * @param <T>
 */
public abstract class AsJsonSerializer<T> extends AbstractJsonSerializer<Object> {

	@SuppressWarnings("unchecked")
	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		// System.err.println("BaseJsonSerializer value:" + value);
		String fieldName = gen.getOutputContext().getCurrentName();
		getCurrentField(gen);

		gen.writeObject(value);
		Class<?> voClazz = null;
		Object data;
		if (value instanceof List) {
			List<Object> list = new ArrayList<Object>();
			for (T key : (List<T>) value) {
				Object element = this.get(key, voClazz);
				list.add(element);
			}
			data = list;
		}
		else {
			data = this.get((T) value, voClazz);
		}

		String newFieldName = this.getFieldName(fieldName);
		gen.writeFieldName(newFieldName);
		gen.writeObject(data);
	}

	/**
	 * 获取VO的属性值
	 * 
	 * @param gen
	 * @param fieldName
	 * @return
	 */
	protected Field getCurrentField(JsonGenerator gen) {
		String target = gen.getOutputTarget().getClass().getName();
		System.err.println("target:" + target);
		return null;
	}

	protected String getFieldName(String fieldName) {
		if ("uid".equals(fieldName)) {
			return "user";
		}
		if ("uidList".equals(fieldName)) {
			return "userList";
		}
		return fieldName.replace("Id", "");
	}

	public abstract Object get(T value, Class<?> voClazz);
}
