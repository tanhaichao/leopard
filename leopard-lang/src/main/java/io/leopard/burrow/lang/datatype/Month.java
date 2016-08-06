package io.leopard.burrow.lang.datatype;

import io.leopard.burrow.util.DateTime;

import java.io.Serializable;

/**
 * 精确到月份的日期(如:2013-01)
 * 
 * @author 阿海
 * 
 */
public class Month implements Serializable {

	private static final long serialVersionUID = 1L;

	private final long time;

	public Month() {
		this(System.currentTimeMillis());
		new Exception("Month").printStackTrace();
	}

	public Month(java.util.Date date) {
		this(date.getTime());
	}

	public Month(String datetime) {
		this(getMillis(datetime));
	}

	public Month(long time) {
		this.time = time;
	}

	public long getTime() {
		return this.time;
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

	@Override
	public String toString() {
		return DateTime.getTime(time).substring(0, 7);
	}

}
