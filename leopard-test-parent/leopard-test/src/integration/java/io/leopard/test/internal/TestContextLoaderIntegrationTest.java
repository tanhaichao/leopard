package io.leopard.test.internal;

import org.junit.Test;

public class TestContextLoaderIntegrationTest {

	@Test
	public void getModuleConfig() {
		String config = new TestContextLoader().getModuleConfig();
		System.out.println("config:" + config);
	}

}