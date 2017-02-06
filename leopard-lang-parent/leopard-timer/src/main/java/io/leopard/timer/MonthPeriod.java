package io.leopard.timer;

import java.util.Calendar;

/**
 * 每月
 * 
 * @author 谭海潮
 *
 */
public class MonthPeriod implements Period {

	private final int day;
	private final int hour;
	private final int minute;
	private final int second;

	/**
	 * 构造周期 .
	 * 
	 * @param hour 小时
	 * @param minute 分钟
	 */
	public MonthPeriod(int day, int hour, int minute) {
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = 0;
	}

	/**
	 * 返回与指定时间点相差的秒数 .
	 * 
	 * @return
	 */
	public int getSeconds() {
		// TODO ahai 如果当月时间未到呢？
		Calendar cld = Calendar.getInstance();
		cld.add(Calendar.MONTH, 1);
		cld.set(Calendar.DAY_OF_MONTH, day);
		cld.set(Calendar.HOUR_OF_DAY, this.hour);
		cld.set(Calendar.MINUTE, this.minute);
		cld.set(Calendar.SECOND, this.second);
		long time = cld.getTimeInMillis();
		long diff = time - System.currentTimeMillis();
		if (diff <= 0) {// 一定要小于等于0
			// 本周的时间已过，下周再执行
			diff += 7 * 24 * 60 * 60 * 1000L;
		}
		int seconds = (int) (diff / 1000);
		return seconds;
	}

	@Override
	public boolean sleep() throws InterruptedException {
		int seconds = this.getSeconds();
		// seconds不能等于0，否则会执行N次
		if (seconds <= 0) {
			seconds = 1;
		}
		// logger.info("seconds:" + seconds);
		long mills = seconds * 1000;
		Thread.sleep(mills);
		return true;
	}

}
