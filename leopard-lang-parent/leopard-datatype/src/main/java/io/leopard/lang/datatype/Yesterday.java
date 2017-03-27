package io.leopard.lang.datatype;

import java.util.Calendar;
import java.util.Date;

/**
 * 昨天(00:00:00)
 * 
 * @author 谭海潮
 *
 */
public class Yesterday extends Date {

	private static final long serialVersionUID = 1L;

	public Yesterday() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		this.setTime(cal.getTimeInMillis());
	}

}
