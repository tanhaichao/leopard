package io.leopard.burrow.lang.datatype;

import java.util.Calendar;
import java.util.Date;

import io.leopard.lang.TimeRange;

/**
 * 月范围
 * 
 * @author 谭海潮
 *
 */
public class MonthRange extends TimeRange {

	public MonthRange() {
		this(0);
	}

	public MonthRange(int monthNum) {
		Date startTime = getFirstDay(monthNum);
		Date endTime = getLastDay(monthNum);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

	/**
	 * 获取第一天
	 * 
	 * @param monthNum
	 * @return
	 */
	public static Date getFirstDay(int monthNum) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthNum);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 获取最后一天
	 * 
	 * @param monthNum
	 * @return
	 */
	public static Date getLastDay(int monthNum) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthNum);
		// cal.set(Calendar.DATE, 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return new Date(cal.getTimeInMillis());
	}

}
