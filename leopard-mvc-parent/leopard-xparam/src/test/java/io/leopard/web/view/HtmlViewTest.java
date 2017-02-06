package io.leopard.web.view;

import io.leopard.web.view.HtmlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class HtmlViewTest {

	@Test
	public void getMessage() {
		HtmlView htmlView = new HtmlView("message");
		Assert.assertEquals("message", htmlView.getMessage());
	}

	@Test
	public void getContentType() {
		HtmlView htmlView = new HtmlView("message");
		Assert.assertEquals("text/html; charset=UTF-8", htmlView.getContentType());

	}

	@Test
	public void getBody() {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		{
			HtmlView htmlView = new HtmlView("<message>");
			Assert.assertEquals("<message>", htmlView.getBody(request, response));
		}
		{
			HtmlView htmlView = new HtmlView("<message>", true);
			Assert.assertEquals("&lt;message&gt;", htmlView.getBody(request, response));
		}
	}

}