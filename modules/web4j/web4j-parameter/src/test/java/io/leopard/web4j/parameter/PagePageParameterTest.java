package io.leopard.web4j.parameter;

import org.junit.Assert;
import org.junit.Test;

public class PagePageParameterTest {
	PagePageParameter page = new PagePageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("page", page.getKey());
	}

}