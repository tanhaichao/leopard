package io.leopard.redis.util;

import io.leopard.redis.util.RedisPool;

import org.junit.Assert;
import org.junit.Test;

public class RedisPoolTest {

	@Test
	public void RedisPool() {
		RedisPool redisPool = new RedisPool("server:6311", 1);
		redisPool.init();
		Assert.assertEquals("server:6311", redisPool.getServer());
		Assert.assertEquals(1, redisPool.getTimeout());
		redisPool.destroy();
	}

}