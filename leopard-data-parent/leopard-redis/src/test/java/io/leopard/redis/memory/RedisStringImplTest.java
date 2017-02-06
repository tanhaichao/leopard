package io.leopard.redis.memory;

import org.junit.Assert;
import org.junit.Test;

public class RedisStringImplTest {

	@Test
	public void get() {
		RedisStringImpl redis = new RedisStringImpl();
		redis.set("key", "value");
		Assert.assertEquals("value", redis.get("key"));
	}

	@Test
	public void setnx() {
		RedisStringImpl redis = new RedisStringImpl();
		Assert.assertEquals(1L, (long) redis.setnx("key", "value"));
		Assert.assertEquals(0L, (long) redis.setnx("key", "value"));
	}

	@Test
	public void set() {
		RedisStringImpl redis = new RedisStringImpl();
		redis.set("key", "value");
		Assert.assertEquals("value", redis.get("key"));
	}

	@Test
	public void incr() {
		RedisStringImpl redis = new RedisStringImpl();
		redis.incr("key");
		Assert.assertEquals("1", redis.get("key"));
	}

	@Test
	public void decrBy() {
		RedisStringImpl redis = new RedisStringImpl();
		Assert.assertEquals(-2L, (long) redis.decrBy("key", 2));
		Assert.assertEquals("-2", redis.get("key"));
		Assert.assertEquals(-3L, (long) redis.decrBy("key", 1));
		Assert.assertEquals("-3", redis.get("key"));
	}

	@Test
	public void decr() {
		RedisStringImpl redis = new RedisStringImpl();
		Assert.assertEquals(-1L, (long) redis.decr("key"));
		Assert.assertEquals(-2L, (long) redis.decr("key"));
		Assert.assertEquals("-2", redis.get("key"));
	}

	@Test
	public void incrBy() {

		RedisStringImpl redis = new RedisStringImpl();
		redis.incrBy("key", 1);
		Assert.assertEquals("1", redis.get("key"));

	}

	@Test
	public void setex() throws InterruptedException {
		RedisStringImpl redis = new RedisStringImpl();
		Assert.assertEquals("value", redis.setex("key", 1, "value"));
		Assert.assertEquals("value", redis.get("key"));
		Thread.sleep(1100);
		Assert.assertNull(redis.get("key"));
	}

	@Test
	public void del() {
		RedisStringImpl redis = new RedisStringImpl();
		Assert.assertEquals("value", redis.set("key", "value"));
		Assert.assertEquals(1L, (long) redis.del("key"));
		Assert.assertEquals(0L, (long) redis.del("key"));
		Assert.assertNull(redis.get("key"));

	}

	@Test
	public void flushAll() {
		RedisStringImpl redis = new RedisStringImpl();
		redis.flushAll();
	}

}