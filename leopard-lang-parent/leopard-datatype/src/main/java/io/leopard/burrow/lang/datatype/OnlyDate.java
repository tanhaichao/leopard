package io.leopard.burrow.lang.datatype;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 精确到天的日期(如:2013-01-01).
 * 
 * @author 阿海
 * 
 */
public class OnlyDate extends Date {

	private static final long serialVersionUID = 1L;

	public OnlyDate() {
		this(System.currentTimeMillis());
	}

	public OnlyDate(java.util.Date date) {
		this(date.getTime());
	}

	public OnlyDate(String datetime) {
		this(getMillis(datetime));
	}

	public OnlyDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		this.setTime(cal.getTimeInMillis());
	}

	protected static long getMillis(String datetime) {
		if (DateTime.isDateTime(datetime)) {
			return DateTime.getTimestamp(datetime);
		}
		else if (DateTime.isDate(datetime)) {
			return DateTime.getTimestamp(datetime + " 00:00:00");
		}
		else {
			throw new IllegalArgumentException("非法参数[" + datetime + "].");
		}
	}

	/**
	 * 获取天数
	 */
	public int getDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.getTime());
		return cal.get(Calendar.DATE);
	}

	private static final SimpleDateFormat GET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String toString() {
		return GET_TIME_FORMAT.format(this);
	}

}
