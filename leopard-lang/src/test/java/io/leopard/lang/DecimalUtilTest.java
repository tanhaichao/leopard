package io.leopard.lang;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class DecimalUtilTest {

	@Test
	public void multiply() {
		double num = DecimalUtil.multiply(4.015, 100);
		System.out.println("num:" + num);
		System.out.println(4.015 * 100);
		System.out.println(new BigDecimal(4.015).multiply(new BigDecimal(100)).doubleValue());
	}

	@Test
	public void multiply2() {
		double num = DecimalUtil.multiply(2.2f, 2);
		System.out.println("num:" + num);
	}

	@Test
	public void multiply3() {
		double num = DecimalUtil.multiply(2.2d, 2);
		System.out.println("num:" + num);
	}

	@Test
	public void scale() {
		Assert.assertEquals(4.03, DecimalUtil.scale(4.0251), 0);
		Assert.assertEquals(4.02, DecimalUtil.scale(4.0241), 0);
		Assert.assertEquals(4.02, DecimalUtil.scale(4.0249), 0);
	}
}