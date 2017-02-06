package io.leopard.test.kit;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jackson.map.util.LRUMap;
import org.junit.Test;

public class MapThreadSafetyAssertIntegrationTest {

	@Test
	public void ConcurrentHashMap() {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		MapThreadSafetyAssert.check(map);
	}

	@Test
	public void HashMap() {
		Map<String, String> map = new HashMap<String, String>();
		MapThreadSafetyAssert.check(map);
	}

	@Test
	public void LinkedHashMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		MapThreadSafetyAssert.check(map);
	}

	@Test
	public void LRUMap() {
		Map<String, String> map = new LRUMap<String, String>(10, 1000000);
		MapThreadSafetyAssert.check(map);
	}

	@Test
	public void synchronizedLRUMap() {
		Map<String, String> map = new LRUMap<String, String>(10, 1000000);
		MapThreadSafetyAssert.check(Collections.synchronizedMap(map));
	}

	@Test
	public void apacheLRUMap() {
		@SuppressWarnings("unchecked")
		Map<String, String> map = new org.apache.commons.collections.map.LRUMap();
		MapThreadSafetyAssert.check(map);
	}
}