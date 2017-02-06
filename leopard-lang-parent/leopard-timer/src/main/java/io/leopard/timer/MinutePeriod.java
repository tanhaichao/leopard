package io.leopard.timer;

/**
 * 周期(精确到分钟)
 * 
 * @author 阿海
 * 
 */
public class MinutePeriod implements Period {

	private final int minute;

	/**
	 * 构造周期 .
	 * 
	 * @param minute
	 *            分钟
	 */
	public MinutePeriod(int minute) {
		this.minute = minute;
	}

	public int getMinute() {
		return minute;
	}

	protected int getSeconds() {
		return getMinute() * 60;
	}

	@Override
	public boolean sleep() throws InterruptedException {
		long mills = getSeconds() * 1000;
		Thread.sleep(mills);
		return true;
	}
}
