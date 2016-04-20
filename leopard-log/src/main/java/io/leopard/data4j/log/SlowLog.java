package io.leopard.data4j.log;

import org.apache.commons.logging.Log;

/**
 * 慢日志.
 * 
 * @author 阿海
 * 
 */
public abstract class SlowLog {
	protected static final Log slowLogger = Log4jFactory.getDataSourceLogger(SlowLog.class);

	private long maxTime;
	private String name;

	public SlowLog(long maxTime, String name) {
		this.maxTime = maxTime;
		this.name = name;
	}

	public Object start() {
		long startTime = System.currentTimeMillis();
		Object result = this.invoke();
		long time = System.currentTimeMillis() - startTime;
		if (time > maxTime) {
			slowLogger.debug(name + " time:" + time);
		}
		return result;
	}

	public abstract Object invoke();
}
