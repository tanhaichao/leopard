package io.leopard.burrow.util;

import java.util.Date;

/**
 * 数据统计日期.
 * 
 * @author 谭海潮
 *
 */
public class StatDateUtil {
	/**
	 * 几天前
	 * 
	 * @param agoDay
	 * @return
	 */
	public static Date getStartTime(int agoDay) {
		return DateUtil.getOnlyDate(DateUtil.addDate(new Date(), agoDay));
	}

	public static Date getEndTime(int agoDay) {
		Date today = DateUtil.getOnlyDate(DateUtil.addDate(new Date(), agoDay));
		return new Date(today.getTime() + DateTime.DAY_MILLIS - 1000L);
	}
}
