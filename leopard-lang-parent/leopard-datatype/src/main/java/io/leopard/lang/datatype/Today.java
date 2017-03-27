package io.leopard.lang.datatype;

import java.util.Calendar;
import java.util.Date;

/**
 * 今天(00:00:00)
 * 
 * @author 谭海潮
 *
 */
public class Today extends Date {

	private static final long serialVersionUID = 1L;

	/**
	 * 00:00:00
	 */
	public Today() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		this.setTime(cal.getTimeInMillis());
	}
}
