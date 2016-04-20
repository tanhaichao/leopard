package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.MockRequest;

import org.junit.Assert;
import org.junit.Test;

public class ProxyIpPageParameterTest {
	ProxyIpPageParameter page = new ProxyIpPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("proxyIp", page.getKey());
	}

	@Test
	public void getValue() {
		MockRequest request = new MockRequest();
		request.addHeader("X-Real-IP", "127.0.0.1");
		Assert.assertEquals("127.0.0.1", page.getValue(request));

	}

}