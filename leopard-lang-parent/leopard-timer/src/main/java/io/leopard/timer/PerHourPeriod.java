package io.leopard.timer;

import java.util.Calendar;

/**
 * 每小时.
 * 
 * @author 阿海
 * 
 */
public class PerHourPeriod implements Period {
	// protected final Log logger = LogFactory.getLog("TIMERLOG." + this.getClass().getName());

	private final int minute;
	private final int second;

	/**
	 * 构造周期 .
	 * 
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 */
	public PerHourPeriod(int minute) {
		this.minute = minute;
		this.second = 0;
	}

	/**
	 * 返回与指定时间点相差的秒数 .
	 * 
	 * @return
	 */
	public int getSeconds() {
		Calendar cld = Calendar.getInstance();
		// cld.set(Calendar.HOUR_OF_DAY, this.hour);
		cld.set(Calendar.MINUTE, this.minute);
		cld.set(Calendar.SECOND, this.second);
		long diff = cld.getTimeInMillis() - System.currentTimeMillis();
		if (diff <= 0) {// 一定要小于等于0
			// 今天的时间已过，明天再执行
			diff += 60 * 60 * 1000L;
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
