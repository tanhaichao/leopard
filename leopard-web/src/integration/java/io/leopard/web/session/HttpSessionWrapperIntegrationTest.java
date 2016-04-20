package io.leopard.web.session;

import io.leopard.web4j.session.HttpSessionWrapper;

import org.junit.Test;

public class HttpSessionWrapperIntegrationTest {

	private HttpSessionWrapper session = new HttpSessionWrapper("sid", 3600, null);

	// private Memcache memcache = MemcacheFactory.createMemoryImpl();//

	@Test
	public void getAttribute() {
		// SessionServiceImpl.memcache = memcache;
		session.setAttribute("key", "value");

		Object value = session.getAttribute("key");
		System.out.println("value:" + value);
	}
}
