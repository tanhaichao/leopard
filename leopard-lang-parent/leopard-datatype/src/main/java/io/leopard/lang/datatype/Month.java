package io.leopard.lang.datatype;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 精确到月份的日期(如:2013-01)
 * 
 * @author 阿海
 * 
 */
public class Month extends Date {

	private static final long serialVersionUID = 1L;

	public Month() {
		this(System.currentTimeMillis());
	}

	public Month(java.util.Date date) {
		this(date.getTime());
	}

	public Month(String datetime) {
		this(getMillis(datetime));
	}

	public Month(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		this.setTime(cal.getTimeInMillis());
	}

	protected static long getMillis(String datetime) {
		if (datetime.length() == 7) {
			return DateTime.getTimestamp(datetime + "-01 00:00:00");
		}
		else if (DateTime.isDateTime(datetime)) {
			return DateTime.getTimestamp(datetime);
		}
		else if (DateTime.isDate(datetime)) {
			return DateTime.getTimestamp(datetime + " 00:00:00");
		}
		else {
			throw new IllegalArgumentException("非法参数[" + datetime + "].");
		}
	}

	private static final SimpleDateFormat GET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM");

	@Override
	public String toString() {
		return GET_TIME_FORMAT.format(this);
	}

}
