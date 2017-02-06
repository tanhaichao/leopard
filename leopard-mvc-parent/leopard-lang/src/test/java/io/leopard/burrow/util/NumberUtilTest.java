package io.leopard.burrow.util;

import org.junit.Test;

public class NumberUtilTest {

	@Test
	public void percent() {
		double percent = NumberUtil.percent(6, 10);
		System.out.println("percent:" + percent);
	}

}