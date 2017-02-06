package io.leopard.timer;


/**
 * 周期(精确到秒)
 * 
 * @author 阿海
 * 
 */
public class SecondPeriod implements Period {

	private final int second;

	/**
	 * 构造周期 .
	 * 
	 * @param second
	 *            秒
	 */
	public SecondPeriod(int second) {
		this.second = second;
	}

	public int getSeconds() {
		return second;
	}

	@Override
	public boolean sleep() throws InterruptedException {
		long mills = getSeconds() * 1000;
		Thread.sleep(mills);
		return true;
	}
}
