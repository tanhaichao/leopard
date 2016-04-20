package io.leopard.web4j.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class OnlyJsonViewTest {

	@Test
	public void getData() {
		OnlyJsonView onlyJsonView = new OnlyJsonView("message");
		Assert.assertEquals("message", onlyJsonView.getData());
	}

	// @Test
	// public void getJson() {
	// OnlyJsonView onlyJsonView = new OnlyJsonView("message");
	// Assert.assertEquals("\"message\"", onlyJsonView.getJson());
	// }

	@Test
	public void getContentType() {
		OnlyJsonView onlyJsonView = new OnlyJsonView("message");
		Assert.assertEquals("text/plain; charset=UTF-8", onlyJsonView.getContentType());
	}

	@Test
	public void getBody() {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		OnlyJsonView onlyJsonView = new OnlyJsonView("message");
		Assert.assertEquals("\"message\"", onlyJsonView.getBody(request, response));
	}

}