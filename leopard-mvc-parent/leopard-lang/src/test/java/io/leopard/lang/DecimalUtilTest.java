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

	@Test
	public void count() {

		// DecimalUtil.count(11111111111111111111111111.13D);

		System.out.println("long:" + Long.MAX_VALUE);

		// long:9223372036854775807(19位-5位小数点=14位) 99999999999999(14位，9亿亿)

		// Assert.assertEquals(5, DecimalUtil.count(999999999999.191234D));
		Assert.assertEquals(5, DecimalUtil.count(99999999999.191234D));// 999亿
		Assert.assertEquals(5, DecimalUtil.count(99999999999.192234D));
		Assert.assertEquals(5, DecimalUtil.count(99999999999.192238D));
		Assert.assertEquals(5, DecimalUtil.count(99999999999.192234D));
		Assert.assertEquals(5, DecimalUtil.count(99999999999.192294D));
		Assert.assertEquals(2, DecimalUtil.count(99999999999.19D));

		Assert.assertEquals(2, DecimalUtil.count(1.11));
		Assert.assertEquals(3, DecimalUtil.count(1.111));
		Assert.assertEquals(1, DecimalUtil.count(1));
	}

	// @Test
	// public void count2() {
	// DecimalUtil.count2(999999999999.191234D);
	// DecimalUtil.count2(99999999999.191234D);
	// DecimalUtil.count2(9999999999.191234D);
	// // Assert.assertEquals(5, DecimalUtil.count2(999999999999.191234D));
	// // Assert.assertEquals(5, DecimalUtil.count2(999999999999.191234D));
	// }
	
	// @Test
	// public void count2() {
	// // 9999999999999 num3:0.19140625
	// // 9999999999999 num3:0.19140625
	// // 9223372036854 77580
	// // long:922337203685477580
	// DecimalUtil.count2(9999999999999.191D);
	// DecimalUtil.count2(9999999999999.1912D);
	// DecimalUtil.count2(9999999999999.191234D);
	// DecimalUtil.count2(999999999999.1912D);
	// DecimalUtil.count2(99999999999.1912D);
	// DecimalUtil.count2(999999999999.1912D);
	// DecimalUtil.count2(999999999999.191254D);
	// DecimalUtil.count2(99999999999.191234D);
	// DecimalUtil.count2(99999999999.191244D);
	// DecimalUtil.count2(99999999999.191254D);
	// DecimalUtil.count2(99999999999.191234D);
	// }

	@Test
	public void isScale() {
		DecimalUtil.isScale(1);
		DecimalUtil.isScale(1.11);
		try {
			DecimalUtil.isScale(1.111);
			Assert.fail("怎么没有抛异常.");
		}
		catch (IllegalArgumentException e) {

		}
	}

}