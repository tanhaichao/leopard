package io.leopard.burrow.util;

import java.util.Date;

import org.junit.Test;

public class StatDateUtilTest {

	@Test
	public void getStartTime() {
		Date date = StatDateUtil.getStartTime(-7);
		System.out.println(DateTime.getTime(date));
	}

	@Test
	public void getEndTime() {
		Date date = StatDateUtil.getEndTime(0);
		System.out.println(DateTime.getTime(date));
	}

	@Test
	public void test() {
		Date start = StatDateUtil.getStartTime(-7);
		Date end = StatDateUtil.getEndTime(0);
		int dayCount = DateUtil.getDayCount(start, end);
		System.out.println("dayCount:" + dayCount);
	}
}