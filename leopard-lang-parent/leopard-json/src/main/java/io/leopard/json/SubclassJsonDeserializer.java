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
 * 子类Json反序列化
 * 
 * @author 谭海潮
 *
 */
public abstract class SubclassJsonDeserializer<T> extends JsonDeserializer<T> {

	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		// System.err.println("nodenode:" + node.toString());
		String fieldName = getTypeFieldName();
		JsonNode fieldNode = node.get(fieldName);
		if (fieldNode == null) {
			throw new NullPointerException("节点[" + fieldName + "]不存在.");
		}
		String type = fieldNode.asText();

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
				// String text = node2.asText();
				value = Integer.parseInt(node2.asText());// node2.intValue();

				// System.err.println("node2:" + text + " value:" + value);

			}
			else if (type.equals(long.class)) {
				// value = node2.longValue();
				value = Long.parseLong(node2.asText());
			}
			else if (type.equals(float.class)) {
				// value = node2.floatValue();
				value = Float.parseFloat(node2.asText());
			}
			else if (type.equals(double.class)) {
				// value = node2.doubleValue();
				value = Double.parseDouble(node2.asText());
			}
			else if (type.equals(Date.class)) {

				// String text = node2.asText();
				// if (text == null) {
				// value = null;
				// }
				// else {
				long time = Long.parseLong(node2.asText());// node2.intValue();

				// long time = node2.longValue();
				if (time == 0) {
					value = null;
				}
				else {
					value = new Date(time);
				}
				// }
			}
			else if (List.class.equals(type)) {
				// System.err.println("node:" + node.asText());
				value = this.parseList(field, node2);
			}
			else {
				// String json = node2.asText();
				String json = node2.toString();
				value = Json.toObject(json, type);

				// System.err.println(" node2:" + node2.asText());
				// IllegalArgumentException e = new IllegalArgumentException("未知数据类型[" + type.getName() + " fieldName:" + fieldName + "].");
				// e.printStackTrace();
				// throw e;
				// value = null;
			}
			field.set(bean, value);
		}
	}

	protected Object parseList(Field field, JsonNode node) {
		ParameterizedType p = (ParameterizedType) field.getGenericType();
		Class<?> clazz = (Class<?>) p.getActualTypeArguments()[0];

		String json = node.toString();
		return Json.toListObject(json, clazz);

		// if (String.class.equals(clazz)) {
		// return this.parseListString(node);
		// }
		// else if (Integer.class.equals(clazz)) {
		// throw new IllegalArgumentException("未实现List<Integer>");
		// }
		// else if (Long.class.equals(clazz)) {
		// throw new IllegalArgumentException("未实现List<Long>");
		// }
		// else {
		// return this.parseListBean(node, clazz);
		// // throw new IllegalArgumentException("未知泛型[" + clazz.getName() + "].");
		// }
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object parseListBean(JsonNode node, Class<?> clazz) {
		String json = node.toString();
		return Json.toListObject(json, clazz);

		// System.err.println("clazz:" + clazz.getName() + " node:" + node.toString());
		//
		// Iterator<JsonNode> elements = node.elements();
		// // System.err.println("parseListBean elements:" + elements);
		// List list = null;
		// if (elements != null) {
		// list = new ArrayList();
		// while (elements.hasNext()) {
		// JsonNode node3 = elements.next();
		//
		// String text = node3.asText();
		// System.err.println("bean text:" + text + " node3.textValue():" + node3.toString());
		// Object bean = Json.toObject(text, clazz);
		// list.add(bean);
		// }
		// }
		// return list;
	}

	protected Object parseListString(JsonNode node) {
		// FIXME ahai 遇到WorkDelegate时?这里实现是否有问题?
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
