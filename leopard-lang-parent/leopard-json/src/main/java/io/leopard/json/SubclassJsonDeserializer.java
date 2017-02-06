package io.leopard.json;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 子类Json序列号
 * 
 * @author Administrator
 *
 */
public abstract class SubclassJsonDeserializer<T> extends JsonDeserializer<T> {

	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		String fieldName = getTypeFieldName();
		String type = node.get(fieldName).asText();

		Class<T> clazz = findClass(type);
		if (clazz == null) {
			// throw new UnsupportedOperationException("未知类型[" + fieldName + "].");
			return null;
		}
		T bean;
		try {
			bean = clazz.newInstance();
			deserialize(node, clazz, bean);

			Class<?> clazz2 = clazz.getSuperclass();
			while (true) {
				if (clazz2 == null || clazz2.equals(Object.class)) {
					break;
				}
				deserialize(node, clazz2, bean);
				clazz2 = clazz2.getSuperclass();
			}

		}
		catch (InstantiationException e) {
			throw new IOException(e.getMessage(), e);
		}
		catch (IllegalAccessException e) {
			throw new IOException(e.getMessage(), e);
		}

		return bean;
	}

	protected void deserialize(JsonNode node, Class<?> clazz, T bean) throws IllegalArgumentException, IllegalAccessException {
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			Class<?> type = field.getType();
			String fieldName = field.getName();
			JsonNode node2 = node.get(fieldName);
			Object value;
			if (node2 == null) {
				value = null;
				continue;
			}
			if (type.equals(String.class)) {
				value = node2.textValue();
			}
			else if (type.equals(boolean.class)) {
				value = node2.booleanValue();
			}
			else if (type.equals(Boolean.class)) {
				String text = node2.asText();
				if (text == null) {
					value = null;
				}
				else {
					value = node2.booleanValue();
				}
			}
			else if (type.equals(int.class)) {
				value = node2.intValue();
			}
			else if (type.equals(long.class)) {
				value = node2.longValue();
			}
			else if (type.equals(float.class)) {
				value = node2.floatValue();
			}
			else if (type.equals(double.class)) {
				value = node2.doubleValue();
			}
			else if (type.equals(Date.class)) {

				// String text = node2.asText();
				// if (text == null) {
				// value = null;
				// }
				// else {
				long time = node2.longValue();
				if (time == 0) {
					value = null;
				}
				else {
					value = new Date(time);
				}
				// }
			}
			else if (List.class.equals(type)) {
				value = this.parseList(field, node2);
			}
			else {
				// System.err.println("textValue:" + textValue + " node2:" + node2.asText());
				IllegalArgumentException e = new IllegalArgumentException("未知数据类型[" + type.getName() + " fieldName:" + fieldName + "].");
				e.printStackTrace();
				// throw e;
				value = null;
			}
			field.set(bean, value);
		}
	}

	protected Object parseList(Field field, JsonNode node) {
		ParameterizedType p = (ParameterizedType) field.getGenericType();
		Class<?> clazz = (Class<?>) p.getActualTypeArguments()[0];
		if (String.class.equals(clazz)) {
			return this.parseListString(node);
		}
		else {
			throw new IllegalArgumentException("未知泛型[" + clazz.getName() + "].");
		}
	}

	protected Object parseListString(JsonNode node) {
		Iterator<JsonNode> elements = node.elements();
		List<String> list = null;
		if (elements != null) {
			list = new ArrayList<String>();
			while (elements.hasNext()) {
				JsonNode node3 = elements.next();
				String text = node3.asText();
				list.add(text);
			}
		}
		return list;
	}

	protected abstract String getTypeFieldName();

	protected abstract Class<T> findClass(String type);

}
