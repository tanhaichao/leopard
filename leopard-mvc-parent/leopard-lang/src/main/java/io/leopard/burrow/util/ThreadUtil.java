package io.leopard.burrow.util;

public class ThreadUtil {
	/**
	 * 休眠</br>
	 * 
	 * @param mills
	 *            休眠时间，单位毫秒
	 */
	public static void sleep(long mills) {
		try {
			Thread.sleep(mills);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
