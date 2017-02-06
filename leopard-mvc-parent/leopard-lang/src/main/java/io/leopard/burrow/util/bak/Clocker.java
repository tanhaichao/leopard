package io.leopard.burrow.util.bak;

//import org.apache.log4j.Level;

public class Clocker {
	private long start;
	private long time;

	/**
	 * 启动计时器 .
	 * 
	 * @return
	 */
	public static Clocker start() {
		Clocker clock = new Clocker();
		clock.start = System.nanoTime();
		return clock;
	}

	/**
	 * 返回耗时，毫微秒为单位 .
	 * 
	 * @return
	 */
	private long nanoTime() {
		long end = System.nanoTime();
		long time = end - start;
		this.start = end;
		this.time = time;
		return time;
	}

	/**
	 * 输出耗时到日志，用于调试 .
	 * 
	 * @param url
	 * @return
	 */
	public long log(String url) {
		long nanoTime = this.nanoTime();
		long ms = nanoTime / 1000 / 1000;
		String message = ms + " " + nanoTime + " " + url;
		System.out.println(message);
		return time;
	}

}
