package io.leopard.redis.hash;

import io.leopard.redis.LongHashType;

import org.junit.Assert;
import org.junit.Test;

public class LongHashTypeTest {

	@Test
	public void getHashCode() {
		Assert.assertEquals(123L, new LongHashType().getHashCode("prefix:123"));
	}

}