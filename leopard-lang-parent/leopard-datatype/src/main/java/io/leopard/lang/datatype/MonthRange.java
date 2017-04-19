package io.leopard.lang.datatype;

import java.util.Calendar;
import java.util.Date;

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
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthNum);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date startTime = new Date(cal.getTimeInMillis());
		this.init(startTime);
	}

	public MonthRange(Date startTime) {
		this.init(startTime);
	}

	protected void init(Date startTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startTime.getTime());
		Date endTime = null;
		{
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			endTime = new Date(cal.getTimeInMillis());
		}

		this.setStartTime(startTime);
		this.setEndTime(endTime);

		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

}
