package io.leopard.data.cacher;

/**
 * 时效类型.
 * 
 * @author 阿海
 * 
 */
public enum Expiry {
	// halfhour
	/**
	 * 秒
	 */
	SECOND(1),
	/**
	 * 分钟
	 */
	MINUTE(60),
	/**
	 * 5分钟
	 */
	FIVE_MINUTE(300),
	/**
	 * 10分钟
	 */
	TEN_MINUTE(600),
	/**
	 * 半小时(30分钟)
	 */
	HALF_HOUR(1800),
	/**
	 * 1个小时
	 */
	HOUR(3600),
	/**
	 * 1天
	 */
	DAY(3600 * 24),
	/**
	 * 1周
	 */
	WEEK(3600 * 24 * 7);

	private int seconds;

	private Expiry(int seconds) {
		this.seconds = seconds;
	}

	public int getSeconds() {
		return this.seconds;
	}
}
