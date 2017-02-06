package io.leopard.timer;

/**
 * 定时器周期.
 * 
 * @author 阿海
 * 
 */
public interface Period {

	
	/**
	 * 睡眠到周期到了 .
	 * 
	 * @return 是否还继续下一次.
	 * @throws InterruptedException
	 */
	boolean sleep() throws InterruptedException;
}
