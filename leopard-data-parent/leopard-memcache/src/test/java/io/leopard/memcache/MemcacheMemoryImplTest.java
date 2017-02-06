package io.leopard.memcache;

import org.junit.Assert;
import org.junit.Test;

public class MemcacheMemoryImplTest {
	MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();

	@Test
	public void remove() {
		MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();
		memcache.put("key", "value");
		Assert.assertTrue(memcache.remove("key"));
		Assert.assertFalse(memcache.remove("key"));
		Assert.assertNull(memcache.get("key"));
	}

	@Test
	public void put() {
		MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();
		memcache.put("key", "value");
		Assert.assertEquals("value", memcache.get("key"));
	}

	@Test
	public void putInt() {
		{
			MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();
			memcache.put("key", 1);
			Assert.assertEquals("1", memcache.get("key"));
		}
		{
			MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();
			memcache.put("key", 1, 1);
			Assert.assertEquals("1", memcache.get("key"));
		}
	}

	@Test
	public void put2() {
		MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();
		memcache.put("key", "value", 1);
		Assert.assertEquals("value", memcache.get("key"));
	}

	// @Test
	// public void get() {
	// MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();
	// memcache.put("key", "value");
	// Assert.assertEquals("value", memcache.get("key"));
	//
	// {
	// memcache.put("key", Json.toJson("value"));
	// Assert.assertEquals("value", memcache.get("key", String.class));
	// }
	// }

	@Test
	public void mget() {
		try {
			memcache.mget(new String[] { "key1", "key2" });
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		// try {
		// memcache.mget(new String[] { "key1", "key2" }, String.class);
		// Assert.fail("怎么没有抛异常?");
		// }
		// catch (NotImplementedException e) {
		//
		// }
	}

	// @Test
	// public void putObject() {
	// try {
	// memcache.putObject("key", "");
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (NotImplementedException e) {
	//
	// }
	// try {
	// memcache.putObject("key", "", 1);
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (NotImplementedException e) {
	//
	// }
	// }

	// @Test
	// public void getObject() {
	// try {
	// memcache.getObject("key");
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (NotImplementedException e) {
	//
	// }
	// }

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
	public void incr() {
		MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();
		Assert.assertEquals(1L, memcache.incr("key"));
		Assert.assertEquals(2L, memcache.incr("key"));
		// try {
		// memcache.incr("key");
		// Assert.fail("怎么没有抛异常?");
		// }
		// catch (NotImplementedException e) {
		//
		// }
	}

	@Test
	public void addOrIncr() {
		try {
			memcache.addOrIncr("key", 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getInt() {
		try {
			memcache.getInt("key");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void add2() {
		MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();
		memcache.add("key", "value", 1);
		Assert.assertEquals("value", memcache.get("key"));
	}

	@Test
	public void add() {
		MemcacheMemoryImpl memcache = new MemcacheMemoryImpl();
		memcache.add("key", "value");
		Assert.assertEquals("value", memcache.get("key"));

		// try {
		// memcache.add("key", "value");
		// Assert.fail("怎么没有抛异常?");
		// }
		// catch (NotImplementedException e) {
		//
		// }
		// try {
		// memcache.add("key", "value", 1);
		// Assert.fail("怎么没有抛异常?");
		// }
		// catch (NotImplementedException e) {
		//
		// }
	}

}