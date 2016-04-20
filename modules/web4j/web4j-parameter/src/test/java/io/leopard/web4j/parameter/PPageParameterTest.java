package io.leopard.web4j.parameter;

import org.junit.Assert;
import org.junit.Test;

public class PPageParameterTest {

	PPageParameter page = new PPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("p", page.getKey());
	}

}