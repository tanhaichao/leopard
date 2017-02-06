package io.leopard.data.kit.newcount;

import org.junit.Assert;
import org.junit.Test;

import io.leopard.redis.Redis;
import io.leopard.redis.util.RedisFactory;

public class NewCounterKitRedisImplTest {

	private NewCounterKitRedisImpl newCounterKitRedisImpl = new NewCounterKitRedisImpl();

	public NewCounterKitRedisImplTest() {
		Redis redis = RedisFactory.create("112.126.75.27:6311");
		newCounterKitRedisImpl.setRedis(redis);
		newCounterKitRedisImpl.setKey("new_invited_count");
	}

	@Test
	public void incr() {
		newCounterKitRedisImpl.delete("1");
		Assert.assertEquals(1L, newCounterKitRedisImpl.incr("1", 1));
		Assert.assertEquals(2L, newCounterKitRedisImpl.incr("1", 1));
		Assert.assertEquals(2L, newCounterKitRedisImpl.count("1"));

	}

}