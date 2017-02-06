package io.leopard.timer;

import java.util.Calendar;

public class WeekPeriod implements Period {

	// <bean id="cronTrigger"
	// class="org.springframework.scheduling.quartz.CronTriggerBean">
	// <property name="jobDetail" ref="giftLogJob"/>
	// <property name="cronExpression" value="0 0 0 ? * MON"/>
	// </bean>
	//
	// <bean id="cronTrigger2"
	// class="org.springframework.scheduling.quartz.CronTriggerBean">
	// <property name="jobDetail" ref="giftMessageJob"/>
	// <property name="cronExpression" value="0 0 12 ? * FRI"/>
	// </bean>

	// protected final Log logger = LogFactory.getLog("TIMERLOG." + this.getClass().getName());

	private final int weekday;
	private final int hour;
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
	public WeekPeriod(int weekday, int hour, int minute) {
		this.weekday = weekday;
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
		// TODO ahai 未做测试
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.DAY_OF_WEEK, this.weekday + 1);
		cld.set(Calendar.HOUR_OF_DAY, this.hour);
		cld.set(Calendar.MINUTE, this.minute);
		cld.set(Calendar.SECOND, this.second);
		long time = cld.getTimeInMillis();
		// System.out.println("time:" +
		// DateUtil.getTime(DateUtil.toDate(time)));
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
