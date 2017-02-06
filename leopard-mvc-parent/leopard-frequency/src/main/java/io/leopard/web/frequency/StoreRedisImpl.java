package io.leopard.web.frequency;

import io.leopard.redis.Redis;

import javax.servlet.http.HttpServletRequest;

public class StoreRedisImpl implements IStore {

	private static Redis redis;

	public static void setRedis(Redis redis) {
		StoreRedisImpl.redis = redis;
	}

	public static Redis getRedis() {
		if (redis == null) {
			throw new RuntimeException("redis未设置.");
		}
		return redis;
	}

	@Override
	public Object getPassport(HttpServletRequest request, Object handler) {
		return request.getAttribute("passport");
	}

	@Override
	public boolean set(String key) {
		Long result = getRedis().setnx(key, "");
		return (result > 0);
	}

	@Override
	public void expire(String key, int seconds) {
		getRedis().expire(key, seconds);
	}

}
