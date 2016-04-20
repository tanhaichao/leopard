package io.leopard.web4j.parameter;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;

public class SessUserIdPageParameterTest {
	SessUserIdPageParameter page = new SessUserIdPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("sessUserId", page.getKey());
	}

	@Test
	public void getValue() {
		try {
			page.getValue(null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (NotImplementedException e) {

		}
	}

}