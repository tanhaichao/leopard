package io.leopard.web.passport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class PassportInterceptorTest {

	@Autowired
	private PassportInterceptor interceptor;

	@Test
	public void preHandle() throws Exception {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		Object handler = null;
		interceptor.preHandle(request, response, handler);
	}

}