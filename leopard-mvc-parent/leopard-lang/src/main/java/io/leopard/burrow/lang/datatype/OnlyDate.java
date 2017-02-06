package io.leopard.burrow.lang.datatype;

import io.leopard.burrow.util.DateTime;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 精确到天的日期(如:2013-01-01).
 * 
 * @author 阿海
 * 
 */
public class OnlyDate implements Serializable {

	private static final long serialVersionUID = 1L;

	private final long time;

	public OnlyDate() {
		this(System.currentTimeMillis());
	}

	public OnlyDate(java.util.Date date) {
		this(date.getTime());
	}

	public OnlyDate(String datetime) {
		this(getMillis(datetime));
		// new Exception("OnlyDate").printStackTrace();
	}

	public OnlyDate(long time) {
		this.time = time;
	}

	public long getTime() {
		return this.time;
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

	public Date toDate() {
		return new Date(time);
	}

	public int getDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal.get(Calendar.DATE);
	}

	@Override
	public String toString() {
		return DateTime.getTime(time).substring(0, 10);
	}

}
