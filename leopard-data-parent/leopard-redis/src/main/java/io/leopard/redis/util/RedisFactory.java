package io.leopard.redis.util;

import io.leopard.redis.RedisImpl;

public class RedisFactory {

	public static RedisImpl create(String server) {
		RedisImpl redis = new RedisImpl(server, 10, 0, false, 3000);
		redis.init();
		return redis;
	}
}
