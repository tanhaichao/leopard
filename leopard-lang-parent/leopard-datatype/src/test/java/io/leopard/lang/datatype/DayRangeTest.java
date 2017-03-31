package io.leopard.lang.datatype;

import java.util.Date;

import org.junit.Test;

public class DayRangeTest {

	@Test
	public void DayRange() {
		DayRange range = new DayRange(new Date(1490284799000L), 0);
		System.out.println("start:" + range.toStartTime() + " end:" + range.toEndTime());
	}

}