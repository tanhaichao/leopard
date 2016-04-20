package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.MockRequest;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Test;

public class CookieUidPageParameterTest {

	CookieUidPageParameter page = new CookieUidPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("cookieYyuid", page.getKey());
	}

	@Test
	public void getValue() {
		Cookie cookie = new Cookie("yyuid", "1234");
		MockRequest request = new MockRequest();
		request.setCookies(cookie);

		Assert.assertEquals("1234", page.getValue(request));
	}

}