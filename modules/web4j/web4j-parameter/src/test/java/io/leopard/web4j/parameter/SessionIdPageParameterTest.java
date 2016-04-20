package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.MockRequest;
import io.leopard.web4j.passport.SessionUtil;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Test;

public class SessionIdPageParameterTest {
	SessionIdPageParameter page = new SessionIdPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("sessionId", page.getKey());
	}

	@Test
	public void getValue() {
		MockRequest request = new MockRequest();

		{
			try {
				page.getValue(request);
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}
		}
		{
			request.setAttribute(SessionUtil.SESSIONID_COOKIE_NAME, "sid1");
			Assert.assertEquals("sid1", page.getValue(request));
		}
		{
			Cookie cookie = new Cookie(SessionUtil.SESSIONID_COOKIE_NAME, "sid2");
			request.setCookies(cookie);
			Assert.assertEquals("sid2", page.getValue(request));
		}
	}
}