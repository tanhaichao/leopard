package io.leopard.data4j.log;

import org.apache.commons.logging.Log;

public interface LogDao {
	Log getLogger(String name, Level level, String filename);

	Log getLogger(String name, Level level, String filename, boolean bufferedIO);

	Log getLogger(Class<?> clazz, Level level, String filename);

	Log getLogger(Class<?> clazz, Level level, String filename, boolean bufferedIO);

	// void addAppender(Logger logger, Level level, String filename, String conversionPattern, boolean bufferedIO);

	/**
	 * 禁止类初始化log.
	 */
	void disabledBeanLog();

	/**
	 * 禁止公用包log.
	 */
	void disabledCommonsLog();
}
