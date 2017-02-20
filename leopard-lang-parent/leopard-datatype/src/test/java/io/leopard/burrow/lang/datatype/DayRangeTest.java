package io.leopard.burrow.lang.datatype;

import org.junit.Test;

public class DayRangeTest {

	@Test
	public void DayRange() {
		DayRange range = new DayRange(0);
		System.out.println("start:" + range.toStartTime() + " end:" + range.toEndTime());
	}

}