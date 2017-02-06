package io.leopard.timer;


public interface Timer {

	
	/**
	 * 是否启用 .
	 * 
	 * @return
	 */
	boolean isEnabled();

	/**
	 * 周期.
	 * 
	 * @return
	 */
	Period getPeriod();

	/**
	 * 线程数.
	 * 
	 * @return
	 */
	int getThreadCount();

	void start();
}
