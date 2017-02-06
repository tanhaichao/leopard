package io.leopard.timer;

/**
 * 周期毫秒.
 * 
 * @author 阿海
 * 
 */
public class MilliSecondPeriod implements Period {
	private final long milliSecond;

	/**
	 * 构造周期 .
	 * 
	 * @param second
	 *            秒
	 */
	public MilliSecondPeriod(long milliSecond) {
		this.milliSecond = milliSecond;
	}

	public long getMilliSecond() {
		return milliSecond;
	}

	@Override
	public boolean sleep() throws InterruptedException {
		long mills = this.getMilliSecond();
		Thread.sleep(mills);

		// try {
		// Thread.sleep(milliSecond);
		// }
		// catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		return true;
	}

}
