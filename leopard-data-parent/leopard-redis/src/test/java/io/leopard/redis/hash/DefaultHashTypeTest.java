package io.leopard.redis.hash;

import io.leopard.redis.DefaultHashType;

import org.junit.Assert;
import org.junit.Test;

public class DefaultHashTypeTest {

	@Test
	public void getHashCode() {
		long hashCode = new DefaultHashType().getHashCode("key");
		// System.out.println("hashCode:" + hashCode);
		Assert.assertEquals(106079L, hashCode);
	}

}