package io.leopard.redis;

import io.leopard.redis.JedisPool;
import io.leopard.redis.RedisConnectionListener;

import org.junit.Test;
import org.mockito.Mockito;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTest {

	@Test
	public void JedisPool() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(1);
		String host = "172.17.1.236";
		int port = 6311;
		int timeout = 3000;
		JedisPool pool = new JedisPool(poolConfig, host, port, timeout, null);
		{
			Jedis jedis = pool.getResource();
			pool.returnBrokenResource(jedis);
			pool.returnResource(jedis);
		}

		{
			RedisConnectionListener redisConnectionListener = Mockito.mock(RedisConnectionListener.class);
			pool.redisConnectionListener = redisConnectionListener;
			Jedis jedis = pool.getResource();
			pool.returnBrokenResource(jedis);
			pool.returnResource(jedis);
		}

	}
}