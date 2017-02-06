package io.leopard.web.session;

import io.leopard.redis.Redis;

public class StoreRedisImpl implements IStore {

	private static Redis redis;

	public static void setRedis(Redis redis) {
		 StoreRedisImpl.redis = redis;
	}

	@Override
	public boolean isEnable() {
		return redis != null;
	}

	@Override
	public String get(String key) {
		// System.err.println("StoreRedisImpl get:" + key);
		return redis.get(key);
	}

	@Override
	public String set(String key, String value, int seconds) {
		return redis.set(key, value, seconds);
	}

	@Override
	public Long del(String key) {
		return redis.del(key);
	}

}
