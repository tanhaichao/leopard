package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.MockRequest;

import org.junit.Assert;
import org.junit.Test;

public class RequestUriPageParameterTest {

	RequestUriPageParameter page = new RequestUriPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("requestUri", page.getKey());
	}

	@Test
	public void getValue() {
		MockRequest request = new MockRequest();
		request.setRequestURI("/index.do");
		Assert.assertEquals("/index.do", page.getValue(request));
	}

}