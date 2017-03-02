package io.leopard.lang.datatype;

import java.util.Date;

import org.junit.Test;

import io.leopard.lang.datatype.DayRange;

public class DayRangeTest {

	@Test
	public void DayRange() {
		DayRange range = new DayRange(new Date(), 3);
		System.out.println("start:" + range.toStartTime() + " end:" + range.toEndTime());
	}

}