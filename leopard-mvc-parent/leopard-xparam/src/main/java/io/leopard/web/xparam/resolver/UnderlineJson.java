package io.leopard.web.xparam.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.leopard.burrow.lang.inum.EnumUtil;
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
	private static <T> List<T> toEnumList(String json, Class<T> clazz) {
		// logger.info("toListObject json:" + json);

		JavaType javaType = getObjectMapper().getTypeFactory().constructParametrizedType(ArrayList.class, List.class, Map.class);

		List<Map<String, Object>> mapList;
		try {
			mapList = getObjectMapper().readValue(json, javaType);
		}
		catch (Exception e) {
			logger.error("clazz:" + clazz.getName() + " json:" + json);
			throw new JsonException(e.getMessage(), e);
		}
		List<T> list = new ArrayList<T>();
		for (Map<String, Object> map : mapList) {
			Object key = map.get("key");
			list.add((T) EnumUtil.toEnum(key, (Class<Enum>) clazz));
		}
		return list;
	}

	public static <T> List<T> toListObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		if (clazz.isEnum()) {
			return toEnumList(json, clazz);
		}

		JavaType javaType = getObjectMapper().getTypeFactory().constructParametrizedType(ArrayList.class, List.class, clazz);
		try {
			return getObjectMapper().readValue(json, javaType);
		}
		catch (Exception e) {
			logger.error("clazz:" + clazz.getName() + " json:" + json);
			throw new JsonException(e.getMessage(), e);
		}
	}
}
