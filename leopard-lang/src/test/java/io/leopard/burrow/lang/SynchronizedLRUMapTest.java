package io.leopard.burrow.lang;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class SynchronizedLRUMapTest {

	@Test
	public void SynchronizedLRUMap() {
		SynchronizedLRUMap<String, String> map = new SynchronizedLRUMap<String, String>(2, 2);

		Assert.assertTrue(map.isEmpty());
		map.put("key1", "value");
		map.put("key2", "value");
		Assert.assertEquals(2, map.size());
		Assert.assertTrue(map.containsKey("key2"));
		map.put("key3", "value");
		Assert.assertEquals(2, map.size());

	}

	@Test
	public void containsValue() {
		SynchronizedLRUMap<String, String> map = new SynchronizedLRUMap<String, String>(2, 2);
		map.put("key1", "value");

		Assert.assertTrue(map.containsValue("value"));
	}

	@Test
	public void get() {
		SynchronizedLRUMap<String, String> map = new SynchronizedLRUMap<String, String>(2, 2);
		map.put("key1", "value");

		Assert.assertEquals("value", map.get("key1"));
	}

	@Test
	public void remove() {
		SynchronizedLRUMap<String, String> map = new SynchronizedLRUMap<String, String>(2, 2);
		map.put("key1", "value");
		map.remove("key1");
		Assert.assertNull(map.get("key1"));
	}

	@Test
	public void clear() {
		SynchronizedLRUMap<String, String> map = new SynchronizedLRUMap<String, String>(2, 2);
		map.put("key1", "value");
		map.clear();
		Assert.assertEquals(0, map.size());
	}

	@Test
	public void putAll() {
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("key1", "value");
		map2.put("key2", "value");
		SynchronizedLRUMap<String, String> map = new SynchronizedLRUMap<String, String>(2, 2);
		map.putAll(map2);
		Assert.assertEquals(2, map.size());

		Assert.assertEquals(2, map.keySet().size());
		Assert.assertEquals(2, map.values().size());
		Assert.assertEquals(2, map.entrySet().size());
	}

}