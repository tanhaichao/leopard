package io.leopard.web4j.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class WsViewTest {

	@Test
	public void getBody() {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		WsView webserviceView = new WsView("data");
		String body = webserviceView.getBody(request, response);
		// System.out.println("body:" + body);
		Assert.assertEquals("{\"status\":200,\"message\":\"\",\"clazz\":\"java.lang.String\",\"data\":\"\\\"data\\\"\"}", body);

		Assert.assertEquals("text/plain; charset=UTF-8", webserviceView.getContentType());
	}

	@Test
	public void getData() {
		WsView webserviceView = new WsView("success");
		Assert.assertEquals("success", webserviceView.getData());
		Assert.assertEquals(200, webserviceView.getStatus());
		Assert.assertEquals("", webserviceView.getMessage());
	}
}