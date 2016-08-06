package io.leopard.lang;

import java.math.BigDecimal;

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
}