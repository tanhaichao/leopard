package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.MockRequest;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Test;

public class CookieUsernamePageParameterTest {

	CookieUsernamePageParameter page = new CookieUsernamePageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("cookieUsername", page.getKey());
	}

	@Test
	public void getValue() {
		Cookie cookie = new Cookie("username", "hctan");
		MockRequest request = new MockRequest();
		request.setCookies(cookie);

		Assert.assertEquals("hctan", page.getValue(request));
	}
}