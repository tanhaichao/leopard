package io.leopard.lang.datatype;

import org.junit.Test;

import io.leopard.lang.datatype.MonthRange;

public class MonthRangeTest {

	@Test
	public void MonthRange() {
		MonthRange range = new MonthRange(0);
		System.out.println("start:" + range.toStartTime() + " end:" + range.toEndTime());
	}

}