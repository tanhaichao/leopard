package io.leopard.burrow.util;

import org.junit.Test;

public class NumberUtilTest {

	@Test
	public void percent() {
		double percent = NumberUtil.percent(6, 10);
		System.out.println("percent:" + percent);
	}

	@Test
	public void scale() {
		double totalCount = 1446;
		double count = 92;
		double avg = count / totalCount;
		double percent = (double) (avg * 100);
		double ratio = NumberUtil.scale(percent, 2);
		System.out.println("avg:" + avg + " ratio:" + ratio);
	}

}