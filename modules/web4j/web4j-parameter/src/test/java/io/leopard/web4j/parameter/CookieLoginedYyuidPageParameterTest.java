package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LeopardMockRunner.class)
public class CookieLoginedYyuidPageParameterTest {

	CookieLoginedUidPageParameter page = new CookieLoginedUidPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("cookieLoginedYyuid", page.getKey());
	}

	@Test
	public void getValue() {
		// {
		// PowerMockito.when(UdbUtil.getUdbUser(null, null)).thenReturn(null);
		// Assert.assertEquals("-1", page.getValue(null, null));
		// }
		// {
		// PassportUser udbUser = new PassportUser();
		// udbUser.setYyuid(1234);
		// PowerMockito.when(UdbUtil.getUdbUser(null, null)).thenReturn(udbUser);
		// Assert.assertEquals("1234", page.getValue(null, null));
		// }
	}

}