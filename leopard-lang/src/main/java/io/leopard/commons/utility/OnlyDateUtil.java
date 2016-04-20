package io.leopard.commons.utility;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;
import io.leopard.burrow.util.DateTime;

import java.util.Calendar;

public class OnlyDateUtil {

	/**
	 * 获取月份的天数
	 * 
	 * @param monthNum
	 *            0:表示当前月份 负数：表示前n个月份 整数：表示后n个月份
	 * @return 天数
	 */
	public static int getDayCountOfMonth(final OnlyDate date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.DATE, 1);
		int daynum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return daynum;
	}

	public static OnlyDate addDate(final OnlyDate date, final int daynum) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.DATE, daynum);
		return new OnlyDate(cal.getTimeInMillis());
	}

	/**
	 * 获取两个日期之间相差的天数
	 * 
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            结束日期
	 * @return 天数
	 */
	public static int getDayCount(final OnlyDate date1, OnlyDate date2) {
		int dayCount1 = DateTime.getDayCount(date1.getTime());
		int dayCount2 = DateTime.getDayCount(date2.getTime());
		return (dayCount1 - dayCount2);
	}

	public static OnlyDate getFirstDay(final Month month) {
		OnlyDate date;
		if (month == null) {
			date = new OnlyDate();
		}
		else {
			date = new OnlyDate(month.getTime());
		}
		return getFirstDayOfMonth(date);
	}

	public static OnlyDate getFirstDayOfMonth(final OnlyDate date) {
		return getFirstDayOfMonth(date, 0);
	}

	/**
	 * 根据给定的日期，返回指定monthNum个月后的月份的第一天，格式为yyyy-MM-dd
	 * 
	 * @param date
	 *            日期
	 * @param monthNum
	 *            月数
	 * @return date指定monthNum个月后的月份第一天的日期字符串
	 */
	public static OnlyDate getFirstDayOfMonth(final OnlyDate date, final int monthNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.MONTH, monthNum);
		cal.set(Calendar.DATE, 1);
		return new OnlyDate(cal.getTimeInMillis());
	}

	/**
	 * 根据给定的日期，返回指定monthNum个月后的月份的第一天，格式为yyyy-MM-dd
	 * 
	 * @param date
	 *            日期
	 * @param monthNum
	 *            月数
	 * @return date指定monthNum个月后的月份第一天的日期字符串
	 */
	public static OnlyDate getFirstDayOfWeek(final OnlyDate date, final int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.WEEK_OF_YEAR, weekNum);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// System.out.println("date:" + date.toString() + "dayOfWeek:" +
		// dayOfWeek);
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return addDate(date, -(dayOfWeek - 1));
	}

	public static OnlyDate getLastDayOfMonth(final OnlyDate date) {
		return getLastDayOfMonth(date, 0);
	}

	/**
	 * 根据给定的日期，返回指定monthNum个月后的月份的最后一天，格式为yyyy-MM-dd
	 * 
	 * @param date
	 *            日期
	 * @param monthNum
	 *            月数
	 * @return date指定monthNum个月后的月份第一天的日期字符串
	 */
	public static OnlyDate getLastDayOfMonth(final OnlyDate date, final int monthNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.MONTH, monthNum + 1);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.DATE, -1);
		return new OnlyDate(cal.getTimeInMillis());
	}
}
