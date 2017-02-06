package io.leopard.timer;


public abstract class SimpleTimer implements Timer {

	protected Period period;

	/**
	 * 构造定时器 .
	 * 
	 * @param second
	 */
	public SimpleTimer(int second) {
		this(new SecondPeriod(second));
	}

	public SimpleTimer(Period period) {
		this.period = period;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Period getPeriod() {
		return period;
	}

	@Override
	public int getThreadCount() {
		return 1;
	}

	public abstract void start();

	public void run() {
		TimerUtil.start(this);
	}

}
