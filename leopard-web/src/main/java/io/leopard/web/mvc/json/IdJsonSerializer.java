package io.leopard.web.mvc.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.core.exception.InvalidException;
import io.leopard.web.mvc.AbstractJsonSerializer;

/**
 * 支持单个和列表数据输出
 * 
 * @author 谭海潮
 *
 * @param <T>
 */
public abstract class IdJsonSerializer<T, V> extends AbstractJsonSerializer<Object> {
	protected static Log logger = LogFactory.getLog(IdJsonSerializer.class);

	/**
	 * 是否忽略非法参数异常
	 */
	private static boolean ignoreInvalid = false;

	public static void setIgnoreInvalid(boolean ignoreInvalid) {
		IdJsonSerializer.ignoreInvalid = ignoreInvalid;
	}

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		// System.err.println("BaseJsonSerializer value:" + value);
		String fieldName = gen.getOutputContext().getCurrentName();

		gen.writeObject(value);
		Object data;
		if (ignoreInvalid) {
			try {
				data = getData(value);
			}
			catch (InvalidException e) {
				logger.warn(e.getMessage(), e);
				data = null;
			}
		}
		else {
			data = getData(value);
		}

		String newFieldName = this.getFieldName(fieldName);
		gen.writeFieldName(newFieldName);
		gen.writeObject(data);
	}

	@SuppressWarnings("unchecked")
	protected Object getData(Object value) {
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
		return data;
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

	public abstract V get(T value);
}
