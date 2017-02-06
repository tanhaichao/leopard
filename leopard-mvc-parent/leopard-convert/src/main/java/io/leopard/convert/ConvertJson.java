package io.leopard.convert;

import io.leopard.json.DisablingJsonSerializerIntrospector;
import io.leopard.json.JsonException;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertJson {

	private static ObjectMapper mapper = new ObjectMapper(); // 忽略不存在的字段.
	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setAnnotationIntrospector(new DisablingJsonSerializerIntrospector());
	}

	/**
	 * 将对象转成json.
	 * 
	 * @param obj 对象
	 * @return
	 */

	public static String toJson(Object obj) {
		try {
			String json;
			if (obj == null) {
				json = null;
			}
			else {
				json = mapper.writeValueAsString(obj);
			}
			return json;
		}
		catch (Exception e) {
			throw new JsonException(e.getMessage(), e);
		}
	}

	/**
	 * 将Json转换成对象.
	 * 
	 * @param json
	 * @param clazz
	 * @param ignoreUnknownField 是否忽略不存在的字段?
	 * @return
	 */

	public static <T> T toObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		try {
			return mapper.readValue(json, clazz);
		}
		catch (Exception e) {
			throw new JsonException(e.getMessage(), e);
		}
	}

	public static <T> List<T> toListObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		JavaType javaType = mapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class, clazz);
		try {
			return mapper.readValue(json, javaType);
		}
		catch (Exception e) {
			throw new JsonException(e.getMessage(), e);
		}

	}
}
