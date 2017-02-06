package io.leopard.redis.util;

import redis.clients.jedis.Jedis;

public interface IJedisPool {

	Jedis getResource();

	void returnBrokenResource(Jedis jedis);

	void returnResource(Jedis jedis);

	void destroy();

}
