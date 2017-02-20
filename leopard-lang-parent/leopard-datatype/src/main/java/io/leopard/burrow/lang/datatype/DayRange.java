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
public class DayRange extends TimeRange {

	public DayRange() {
		this(0);
	}

	public DayRange(int dayNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date startTime = new Date(cal.getTimeInMillis());
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
	}

}
