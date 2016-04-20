package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LeopardMockRunner.class)
public class CookieLoginedUsernamePageParameterTest {
	CookieLoginedUsernamePageParameter page = new CookieLoginedUsernamePageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("cookieLoginedUsername", page.getKey());
	}

	@Test
	public void getValue() {
		// PowerMockito.when(UdbUtil.getLoginedPassport(null, null)).thenReturn("hctan");
		// Assert.assertEquals("hctan", page.getValue(null, null));
	}

}