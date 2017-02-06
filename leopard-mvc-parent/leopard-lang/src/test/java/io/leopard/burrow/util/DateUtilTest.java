package io.leopard.burrow.util;

import java.util.Date;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void getFirstDayOfMonth() {
		System.out.println("date:" + DateTime.getTime(DateUtil.getFirstDayOfMonth(new Date())));
		System.out.println("date:" + DateTime.getTime(DateUtil.getFirstDayOfMonth(-1)));
	}

}