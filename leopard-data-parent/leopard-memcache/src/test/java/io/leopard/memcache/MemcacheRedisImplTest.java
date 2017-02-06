package io.leopard.memcache;

import io.leopard.redis.RedisMemoryImpl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MemcacheRedisImplTest {

	protected MemcacheRedisImpl memcache = createMemcacheRedisImpl();

	public static MemcacheRedisImpl createMemcacheRedisImpl() {
		MemcacheRedisImpl memcache = new MemcacheRedisImpl();
		memcache.redis = new RedisMemoryImpl();
		return memcache;
	}

	@Test
	public void put() {
		memcache.put("key", "value");
		Assert.assertEquals("value", memcache.get("key"));

		memcache.put("key", 1);
		Assert.assertEquals(1, memcache.getInt("key"));
		memcache.put("key", 2, 1);
		Assert.assertEquals(2, memcache.getInt("key"));
	}

	@Test
	public void remove() {

		memcache.put("key", "value");
		memcache.remove("key");
		Assert.assertNull(memcache.get("key"));
	}

	@Test
	public void flushAll() {
		try {
			memcache.flushAll();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getServer() {
		memcache.setServer("server");
		Assert.assertEquals("server", memcache.getServer());
	}

	@Test
	public void init() {
		try {
			memcache.init();
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		memcache.setServer("server");
		this.memcache.init();
	}

	@Test
	public void destroy() {

		memcache.destroy();
		memcache.redis = null;
		memcache.destroy();
	}

	@Test
	public void incr() {
		memcache.remove("key");
		memcache.incr("key");
		Assert.assertEquals(1, memcache.getInt("key"));
	}

	@Test
	public void addOrIncr() {
		memcache.remove("key");
		memcache.addOrIncr("key", 2);
		Assert.assertEquals(2, memcache.getInt("key"));
	}

	@Test
	public void mget() {
		memcache.put("key1", "value1");
		memcache.put("key2", "value2");

		List<String> list = memcache.mget(new String[] { "key1", "key2" });
		Assert.assertEquals("[value1, value2]", list.toString());
	}

	@Test
	public void add() {
		memcache.add("key1", "value11");
		memcache.add("key2", "value22", 1);

		List<String> list = memcache.mget(new String[] { "key1", "key2" });
		Assert.assertEquals("[value11, value22]", list.toString());
	}

	// @Test
	// public void get() {
	// String json = Json.toJson("str");
	// memcache.put("key", json);
	// Assert.assertEquals("str", memcache.get("key", String.class));
	// }

	@Test
	public void getInt() {
		Assert.assertEquals(-1, memcache.getInt("keyxxx"));
	}

}