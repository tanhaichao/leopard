package io.leopard.web4j.view;

import io.leopard.test4j.mock.MockRequest;
import io.leopard.test4j.mock.MockResponse;

import org.junit.Assert;
import org.junit.Test;

public class AjaxViewTest {

	@Test
	public void AjaxView() {

	}

	@Test
	public void getBody() {
		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		AjaxView view = new AjaxView("data");
		String body = view.getBody(request, response);
		System.out.println("body:" + body);
		Assert.assertEquals("{\"status\":200,\"message\":\"\",\"data\":\"data\"}", body);
	}
}