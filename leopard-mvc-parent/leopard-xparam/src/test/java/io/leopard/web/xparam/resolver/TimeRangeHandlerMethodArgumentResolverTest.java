package io.leopard.web.xparam.resolver;

import org.junit.Test;

public class TimeRangeHandlerMethodArgumentResolverTest {

	@Test
	public void toDate() {
		TimeRangeHandlerMethodArgumentResolver.toDate(System.currentTimeMillis() + "");
	}

}