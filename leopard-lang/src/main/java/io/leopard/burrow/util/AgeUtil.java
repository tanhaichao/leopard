package io.leopard.burrow.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class AgeUtil {

	public static int getAge(String birth) {
		if (StringUtils.isEmpty(birth)) {
			return 0;
		}
		if ("1970-01-01".equals(birth)) {
			return 0;
		}
		long millis = DateTime.getTimestamp(birth + " 00:00:00");
		return getAge(new Date(millis));
	}

	public static int getAge(Date birth) {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birth)) {
			throw new IllegalArgumentException("出生时间大于当前时间!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birth);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		// System.out.println("monthNow:" + monthNow + " monthBirth:" + monthBirth + " dayOfMonthNow:" + dayOfMonthNow + " dayOfMonthBirth:" + dayOfMonthBirth + " age:" + age);

		if (monthBirth == monthNow) {
			if (dayOfMonthBirth < dayOfMonthNow) {
				age++;
			}
		}
		else if (monthBirth < monthNow) {
			age++;
		}
		else if (monthBirth > monthNow) {
			// nothing
		}
		return age - 1;
	}
}
