package io.leopard.web.xparam.resolver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.leopard.burrow.lang.inum.EnumUtil;
import io.leopard.core.exception.invalid.EnumConstantInvalidException;
import io.leopard.json.DisablingJsonSerializerIntrospector;
import io.leopard.json.JsonException;
import io.leopard.json.JsonJacksonImpl.OnumJsonSerializer;

public class UnderlineJson {
	protected static Log logger = LogFactory.getLog(UnderlineJson.class);

	private static ObjectMapper mapper = new ObjectMapper();

	private static ObjectMapper underlineMapper = new ObjectMapper();

	static {

		{
			SimpleModule module = new SimpleModule();
			module.addSerializer(new OnumJsonSerializer());
			// module.addDeserializer(Onum.class, new OnumJsonDeserializer());

			mapper.registerModule(module);
			underlineMapper.registerModule(module);
		}

		mapper.setAnnotationIntrospector(new DisablingJsonSerializerIntrospector());
		underlineMapper.setAnnotationIntrospector(new DisablingJsonSerializerIntrospector());

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		underlineMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		underlineMapper = underlineMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}

	private static ObjectMapper getObjectMapper() {
		// System.err.println("UnderlineNameConfiger.isEnable():" + UnderlineNameConfiger.isEnable());
		if (UnderlineNameConfiger.isEnable()) {
			return underlineMapper;
		}
		else {
			return mapper;
		}
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		try {
			return getObjectMapper().readValue(json, clazz);
		}
		catch (Exception e) {
			throw new JsonException(e.getMessage(), e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> List<T> toEnumListByOnlyKey(String json, Class<T> clazz) {
		JavaType javaType = getObjectMapper().getTypeFactory().constructParametrizedType(ArrayList.class, List.class, Object.class);
		List<Object> keyList;
		try {
			keyList = getObjectMapper().readValue(json, javaType);
		}
		catch (Exception e) {
			logger.error("clazz:" + clazz.getName() + " json:" + json);
			throw new JsonException(e.getMessage(), e);
		}

		// System.err.println("keyList:" + keyList);
		List<T> list = new ArrayList<T>();
		for (Object key : keyList) {
			T onum = (T) EnumUtil.toEnum(key, (Class<Enum>) clazz);
			// System.err.println("key:" + key + " onum:" + onum);
			list.add(onum);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> toEnumList(String json, Class<T> clazz) {
		JavaType javaType = getObjectMapper().getTypeFactory().constructParametrizedType(ArrayList.class, List.class, Map.class);
		List<Map<String, Object>> mapList;
		try {
			mapList = getObjectMapper().readValue(json, javaType);
		}
		catch (Exception e) {
			logger.warn("clazz:" + clazz.getName() + " json2:" + json);
			// throw new JsonException(e.getMessage(), e);
			return toEnumListByOnlyKey(json, clazz);
		}
		List<T> list = new ArrayList<T>();
		for (Map<String, Object> map : mapList) {
			Object key = map.get("key");
			if (key == null) {
				throw new EnumConstantInvalidException("枚举的key不允许为null.");
			}
			list.add((T) EnumUtil.toEnum(key, (Class<Enum>) clazz));
		}
		return list;
	}

	public static <T> List<T> toListObject(String json, Type type) {
		if (json == null || json.length() == 0) {
			return null;
		}
		// if (clazz.isEnum()) {
		// return toEnumList(json, clazz);
		// }

		JavaType javaType;
		try {
			javaType = getObjectMapper().getTypeFactory().constructType(type);
		}
		catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		try {
			return getObjectMapper().readValue(json, javaType);
		}
		catch (Exception e) {
			e.printStackTrace();
			// logger.error("clazz:" + clazz.getName() + " json:" + json);
			throw new JsonException(e.getMessage(), e);
		}
	}

	public static <T> List<T> toListObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		if (clazz.isEnum()) {
			return toEnumList(json, clazz);
		}
		// System.err.println("clazz:" + clazz.getName() + " json:" + json);
		JavaType javaType = getObjectMapper().getTypeFactory().constructParametrizedType(ArrayList.class, List.class, clazz);
		try {
			return getObjectMapper().readValue(json, javaType);
		}
		catch (Exception e) {
			// e.printStackTrace();
			logger.error("clazz:" + clazz.getName() + " json:" + json);
			throw new JsonException(e.getMessage(), e);
		}
	}
}
