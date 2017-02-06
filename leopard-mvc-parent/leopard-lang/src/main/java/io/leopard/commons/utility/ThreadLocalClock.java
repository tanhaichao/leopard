package io.leopard.commons.utility;

public class ThreadLocalClock {

	private static ThreadLocal<Long> TIME = new ThreadLocal<Long>();

	public static void setTime(long time) {
		TIME.set(time);
	}

	public static long getTime() {
		return TIME.get();
	}

}
