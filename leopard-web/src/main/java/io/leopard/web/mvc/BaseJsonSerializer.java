package io.leopard.web.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 支持单个和列表数据输出
 * 
 * @author 谭海潮
 *
 * @param <T>
 */
public abstract class BaseJsonSerializer<T, V> extends AbstractJsonSerializer<Object> {

	@SuppressWarnings("unchecked")
	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		// System.err.println("BaseJsonSerializer value:" + value);
		gen.writeObject(value);
		Object data;
		if (value instanceof List) {
			List<V> list = new ArrayList<V>();
			for (T key : (List<T>) value) {
				V element = this.get(key);
				list.add(element);
			}
			data = list;
		}
		else {
			data = this.get((T) value);
		}
		gen.writeFieldName("carList");
		gen.writeObject(data);
	}

	public abstract V get(T value);
}
