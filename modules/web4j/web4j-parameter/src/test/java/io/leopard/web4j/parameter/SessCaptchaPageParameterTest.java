package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.MockRequest;
import io.leopard.web4j.captcha.CaptchaView;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;

public class SessCaptchaPageParameterTest {
	SessCaptchaPageParameter page = new SessCaptchaPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("sessCaptcha", page.getKey());
	}

	@Test
	public void getValue() {
		String captchaGroupId = "default";
		MockRequest request = new MockRequest();
		request.setAttribute("captchaGroupId", captchaGroupId);
		HttpSession session = request.getSession();
		session.setAttribute(CaptchaView.getSessionKey(captchaGroupId), "code");

		Assert.assertEquals("code", page.getValue(request));

		try {
			page.getValue(request);
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}
}