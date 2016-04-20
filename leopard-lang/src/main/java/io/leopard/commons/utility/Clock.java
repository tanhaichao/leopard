package io.leopard.commons.utility;

import io.leopard.burrow.util.NumberUtil;

import org.apache.commons.logging.Log;

/**
 * 计时器
 * 
 * @author 阿海
 */
public class Clock {

	private long start;
	private long time;
	private int size;

	/**
	 * 启动一个计时器</br>
	 * 
	 * @param size
	 * @return 计时器示例
	 */
	public static Clock start(int size) {
		Clock clock = new Clock();
		clock.size = size;
		clock.start = System.currentTimeMillis();
		return clock;
	}

	/**
	 * 启动一个计时器</br>
	 * 
	 * @return 计时器示例
	 */
	public static Clock start() {
		Clock clock = new Clock();
		clock.start = System.currentTimeMillis();
		return clock;
	}

	public int size() {
		return size;
	}

	/**
	 * 返回消耗的毫秒数</br>
	 * 
	 * @param name
	 * @return 消耗的毫秒数，并打印到控制台
	 */
	public long time(String name) {
		long time = this.time();
		if (name == null || name.length() == 0) {
			System.out.println("time:" + NumberUtil.format(time) + "ms");
		}
		else {
			System.out.println(name + " time:" + NumberUtil.format(time) + "ms");
		}
		return time;
	}

	/**
	 * 返回消耗的毫秒数</br>
	 * 
	 * @param logger
	 *            org.apache.commons.logging.Log
	 * @param name
	 * @return 消耗的毫秒数，并由org.apache.commons.logging.Log输出到日志
	 */
	public long time(Log logger, String name) {
		long time = this.time();
		if (name == null || name.length() == 0) {
			logger.info("time:" + NumberUtil.format(time) + "ms");
		}
		else {
			logger.info(name + " time:" + NumberUtil.format(time) + "ms");
		}
		return time;
	}

	/**
	 * 返回消耗的毫秒数，超过minTime则输出警告信息</br>
	 * 
	 * @param logger
	 *            org.apache.commons.logging.Log
	 * @param name
	 * @param minTime
	 *            限定的耗时毫秒数
	 * @return 消耗的毫秒数，并由org.apache.commons.logging.Log输出到日志
	 */
	public long time(Log logger, String name, long minTime) {
		long time = this.time();
		if (time < minTime) {
			return time;
		}
		if (name == null || name.length() == 0) {
			logger.warn("time:" + NumberUtil.format(time) + "ms");
		}
		else {
			logger.warn(name + " time:" + NumberUtil.format(time) + "ms");
		}
		return time;
	}

	/**
	 * 返回消耗的毫秒数</br>
	 * 
	 * @return 消耗的毫秒数
	 */
	public long time() {
		long end = System.currentTimeMillis();
		long time = end - start;
		this.start = end;
		this.time = time;
		return time;
	}

	public int avg() {
		return this.avg(size);
	}

	protected int avg(int size) {
		if (time <= 0) {
			throw new IllegalArgumentException("时间总耗时不能<=0.");
		}
		// TODO ahai 可能会无用，使用前必须调用,this.time()
		double num = (double) size / (double) this.time;
		double avg = (num * 1000d);
		// System.out.println("avg:" + avg + " num:" + num + " time:" + time);
		return (int) avg;
	}

	public long avg(int size, String message) {
		long time = this.time();
		int avg = this.avg(size);
		System.out.println(message + " avg:" + avg);
		return time;
	}

	public long avg(String message) {
		long time = this.time();
		int avg = this.avg(size);
		System.out.println(message + " avg:" + avg + " time:" + time);
		return time;
	}

	public static int getLevel(long time) {
		int level;
		if (time < 2) {
			level = 1;
		}
		else if (time < 10) {
			level = 2;
		}
		else if (time < 50) {
			level = 3;
		}
		else if (time < 100) {
			level = 4;
		}
		else if (time < 500) {
			level = 5;
		}
		else if (time < 1000) {
			level = 6;
		}
		else {
			level = 9;
		}
		return level;
	}

	/**
	 * 输出消耗的毫秒数到org.apache.commons.logging.Log</br>
	 * 
	 * @param logger
	 *            org.apache.commons.logging.Log
	 * @param message
	 *            打印的信息
	 */
	public void log(Log logger, String message) {
		long time = this.time();
		log(logger, message, time);
	}

	/**
	 * 输出消耗的毫秒数到org.apache.commons.logging.Log</br>
	 * 
	 * @param logger
	 *            org.apache.commons.logging.Log
	 * @param message
	 *            打印的信息
	 * @param time
	 *            消耗的毫秒数
	 */
	public static void log(Log logger, String message, long time) {
		int level = getLevel(time);
		logger.info(message + " time:" + time + "ms, level:" + level);
	}

	/**
	 * 输出消耗的毫秒数到org.apache.commons.logging.Log，用于调试</br>
	 * 
	 * @param logger
	 *            org.apache.commons.logging.Log
	 * @param message
	 *            打印的信息
	 * @param time
	 *            消耗的毫秒数
	 */
	public static void debug(Log logger, String message, long time) {
		int level = getLevel(time);
		logger.debug(message + " time:" + time + "ms, level:" + level);
	}

}
