package io.leopard.web.servlet;

import org.junit.Assert;
import org.junit.Test;

public class CookieBuilderTest {

	@Test
	public void parseTopLevelDomain() {
		Assert.assertEquals("onloon.co", CookieBuilder.parseTopLevelDomain("shop.onloon.co"));
		Assert.assertEquals("onloon.co", CookieBuilder.parseTopLevelDomain("shop.test.onloon.co"));
		Assert.assertEquals("localhost", CookieBuilder.parseTopLevelDomain("localhost"));
	}

}