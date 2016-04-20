package io.leopard.data.alldb;

import io.leopard.burrow.util.NumberUtil;
import io.leopard.json.Json;
import io.leopard.redis.Redis;

public class HashsImpl {

	private Redis redis;

	private String key;

	private String fieldPattern;

	public Redis getRedis() {
		return redis;
	}

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFieldPattern() {
		return fieldPattern;
	}

	public void setFieldPattern(String fieldPattern) {
		this.fieldPattern = fieldPattern;
	}

	public boolean set(Object bean, Object... keys) {
		String field = this.getField(keys);
		String json = Json.toJson(bean);
		Long result = redis.hset(key, field, json);
		return NumberUtil.toBool(result);
	}

	public <T> T get(Class<T> elementType, Object... keys) {
		String field = this.getField(keys);
		String json = redis.hget(field, field);
		return Json.toObject(json, elementType);
	}

	protected String getField(Object... keys) {
		if ("$1".equals(this.fieldPattern)) {
			return keys[0].toString();
		}
		String field = this.fieldPattern;
		for (int i = 0; i < keys.length; i++) {
			field = field.replace("$" + (i + 1), keys[i].toString());
		}
		return key;
	}

}
