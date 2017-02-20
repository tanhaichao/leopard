package io.leopard.burrow.lang.datatype;

import org.junit.Test;

public class MonthRangeTest {

	@Test
	public void MonthRange() {
		MonthRange range = new MonthRange(0);
		System.out.println("start:" + range.toStartTime() + " end:" + range.toEndTime());
	}

}