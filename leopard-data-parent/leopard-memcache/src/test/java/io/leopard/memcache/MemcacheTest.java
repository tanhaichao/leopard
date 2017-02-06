package io.leopard.memcache;

import org.junit.Test;

public class MemcacheTest {

	@Test
	public void testArray() {
		String[] servers = { "a", "b", "c" };

		System.out.println("hashCode:" + servers.hashCode());
	}
}
