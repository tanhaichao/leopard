package io.leopard.redis.util;

import io.leopard.redis.RedisImpl;
import io.leopard.redis.util.RedisFactory;

import org.junit.Assert;
import org.junit.Test;

public class RedisFactoryTest {

	@Test
	public void create() throws Exception {
		RedisImpl redis = RedisFactory.create("server");
		Assert.assertEquals("server", redis.getServerInfo());
		// Assert.assertTrue(redis.equals(this.redis));
	}

	@Test
	public void RedisFactory() {
		new RedisFactory();
	}
}