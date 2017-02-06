package io.leopard.redis.hash;

import io.leopard.redis.StringHashType;

import org.junit.Assert;
import org.junit.Test;

public class StringHashTypeTest {

	@Test
	public void getHashCode() {
		Assert.assertEquals(48690, new StringHashType().getHashCode("prefix:123"));
	}

}