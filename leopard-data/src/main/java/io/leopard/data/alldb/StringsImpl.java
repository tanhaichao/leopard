package io.leopard.data.alldb;

import io.leopard.json.Json;
import io.leopard.redis.Redis;

public class StringsImpl {

	private Redis redis;

	private int seconds;

	private String keyPattern;

	public Redis getRedis() {
		return redis;
	}

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public String getKeyPattern() {
		return keyPattern;
	}

	public void setKeyPattern(String keyPattern) {
		this.keyPattern = keyPattern;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public boolean set(Object bean, Object... keys) {
		String key = this.getKey(keys);
		String json = Json.toJson(bean);
		if (seconds > 0) {
			redis.set(key, json, seconds);// 1小时过期
		}
		else {
			redis.set(key, json);// 1小时过期
		}
		return true;
	}

	public <T> T get(Class<T> elementType, Object... keys) {
		String key = this.getKey(keys);
		System.err.println("strings key:" + key);
		String json = redis.get(key);
		return Json.toObject(json, elementType);
	}

	protected String getKey(Object... keys) {
		String key = keyPattern;
		for (int i = 0; i < keys.length; i++) {
			key = key.replace("$" + (i + 1), keys[i].toString());
		}
		return key;
	}

}
