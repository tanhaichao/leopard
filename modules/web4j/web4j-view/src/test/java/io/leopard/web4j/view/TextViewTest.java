package io.leopard.web4j.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TextViewTest {

	@Test
	public void getMessage() {
		TextView textView = new TextView("message");
		Assert.assertEquals("message", textView.getMessage());
	}

	@Test
	public void getContentType() {
		TextView textView = new TextView("message");
		Assert.assertEquals("text/plain; charset=UTF-8", textView.getContentType());
	}

	@Test
	public void getBody() {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		TextView textView = new TextView("message");
		Assert.assertEquals("message", textView.getBody(request, response));
	}

}