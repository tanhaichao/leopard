package io.leopard.im.huanxin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import io.leopard.json.JsonException;

public class HuanxinJson {
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share
	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
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
}
