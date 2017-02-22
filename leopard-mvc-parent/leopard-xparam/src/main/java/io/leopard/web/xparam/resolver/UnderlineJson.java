package io.leopard.web.xparam.resolver;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import io.leopard.json.JsonException;

public class UnderlineJson {

	private static ObjectMapper mapper = new ObjectMapper();

	private static ObjectMapper underlineMapper = new ObjectMapper();

	@PostConstruct
	public void init() {
		underlineMapper = mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}

	private static ObjectMapper getObjectMapper() {
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

	public static <T> List<T> toListObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		JavaType javaType = getObjectMapper().getTypeFactory().constructParametrizedType(ArrayList.class, List.class, clazz);
		try {
			return getObjectMapper().readValue(json, javaType);
		}
		catch (Exception e) {

			throw new JsonException(e.getMessage(), e);
		}
	}
}
