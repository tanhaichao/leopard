package io.leopard.data4j.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LogDaoLog4jImpl implements LogDao {

	public Log getLogger(String name, Level level, String filename) {
		boolean bufferedIO = true;
		return getLogger(name, level, filename, bufferedIO);
	}

	public Log getLogger(String name, Level level, String filename, boolean bufferedIO) {
		Logger logger = Logger.getLogger(name);
		addAppender(logger, level, filename, bufferedIO);
		return new Log4jAdapter(logger);
	}

	public Log getLogger(Class<?> clazz, Level level, String filename) {
		boolean bufferedIO = true;
		return getLogger(clazz, level, filename, bufferedIO);
	}

	public Log getLogger(Class<?> clazz, Level level, String filename, boolean bufferedIO) {
		Logger logger = Logger.getLogger(clazz);
		addAppender(logger, level, filename, bufferedIO);
		return new Log4jAdapter(logger);
	}

	private void addAppender(Logger logger, Level level, String filename, boolean bufferedIO) {
		org.apache.log4j.Level log4jLevel = toLog4jLevel(level);

		Appender appender = this.getAppender(filename, log4jLevel, bufferedIO);

		logger.setLevel(log4jLevel);
		logger.addAppender(appender);
	}

	private static Map<String, DailyAutoRollingFileAppender> appenderMap = new ConcurrentHashMap<String, DailyAutoRollingFileAppender>();

	protected Appender getAppender(String filename, org.apache.log4j.Level log4jLevel, boolean bufferedIO) {
		String key = filename + ":" + log4jLevel.toInt() + ":" + bufferedIO;

		DailyAutoRollingFileAppender appender = appenderMap.get(key);

		if (appender != null) {
			return appender;
		}

		String conversionPattern = "%d{yyyy-MM-dd HH:mm:ss} - %m%n";

		org.apache.log4j.PatternLayout layout = new PatternLayout();
		layout.setConversionPattern(conversionPattern);

		appender = new DailyAutoRollingFileAppender();
		// DailyRollingFileAppender newAppender = new DailyRollingFileAppender();
		appender.setThreshold(log4jLevel);
		appender.setLayout(layout);
		appender.setDatePattern(".yyyyMMdd");
		appender.setFile(filename);
		appender.setBufferSize(10);// 8192
		appender.setBufferedIO(bufferedIO);
		appender.activateOptions();

		appenderMap.put(key, appender);
		return appender;
	}

	protected org.apache.log4j.Level toLog4jLevel(Level level) {
		if (level.equals(Level.DEBUG)) {
			// System.out.println("return .Level.DEBUG");
			return org.apache.log4j.Level.DEBUG;
		}
		else if (level.equals(Level.INFO)) {
			return org.apache.log4j.Level.INFO;
		}
		else if (level.equals(Level.WARN)) {
			return org.apache.log4j.Level.WARN;
		}
		else if (level.equals(Level.ERROR)) {
			return org.apache.log4j.Level.ERROR;
		}
		throw new IllegalArgumentException("未知level[" + level + "].");
	}

	/**
	 * 禁止类初始化log.
	 */
	public void disabledBeanLog() {
		Logger.getLogger("BEANLOG").setLevel(org.apache.log4j.Level.WARN);
		Logger.getLogger("org.springframework").setLevel(org.apache.log4j.Level.WARN);
	}

	/**
	 * 禁止公用包log.
	 */
	public void disabledCommonsLog() {

	}

}
