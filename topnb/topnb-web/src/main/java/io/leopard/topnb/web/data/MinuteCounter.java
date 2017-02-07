package io.leopard.topnb.web.data;

/**
 * 分钟计数器.
 * 
 * @author 阿海
 *
 */
public class MinuteCounter {

	// private AtomicLong counter = new AtomicLong();
	private static final long PEROID_MILLIS = 2000;// 周期毫秒数.

	private int lastMinute;// 上一分钟的分钟数
	private int lastCount;// 上一分钟的次数
	private double lastTime;// 上一分钟的耗时

	private int minute;// 数据最后更新时间

	private double time;
	private int count;

	public MinuteCounter() {
		// super(type, server);
		this.minute = getMinute();
	}

	protected int getMinute() {
		return (int) (System.currentTimeMillis() / PEROID_MILLIS);
	}

	public synchronized void add(double time, int count) {
		this.time += time;
		this.count += count;
	}

	protected synchronized void check() {
		int minute = getMinute();
		if (minute <= this.minute) {
			return;
		}
		if (this.count > 0 && this.count >= this.lastCount) {
			if (!(lastCount == count && count < 2)) {
				this.lastCount = this.count;
				this.lastMinute = this.minute;
				this.lastTime = this.time;
			}
		}
		this.time = 0;
		this.count = 0;

		this.minute = minute;
	}

	public CountDto upload(long timeLag) {
		this.check();
		if (lastMinute <= 0 || lastTime <= 0) {
			return null;
		}

		int peroidTag = (int) (timeLag / PEROID_MILLIS);
		int minute = this.lastMinute + peroidTag;
		long time = minute * PEROID_MILLIS;

		CountDto countDto = new CountDto();
		countDto.setName("minute:" + time);
		countDto.setCount(this.lastCount);
		countDto.setTime(this.lastTime);

		this.lastTime = 0;

		return countDto;
	}

}
