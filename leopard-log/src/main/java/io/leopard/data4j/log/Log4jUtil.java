package io.leopard.data4j.log;

import org.apache.commons.logging.Log;

public class Log4jUtil {

	
	private static LogDao logDao;

	protected static LogDao getLogDao() {
		if (logDao != null) {
			return logDao;
		}

		try {
			Class.forName("org.apache.log4j.Logger");
			logDao = new LogDaoLog4jImpl();
		}
		catch (ClassNotFoundException e) {
			logDao = new LogDaoInterfaceImpl();
		}

		return logDao;
	}

	public static Log getLogger(String name, Level level, String filename) {
		return getLogDao().getLogger(name, level, filename);
	}

	public static Log getLogger(String name, Level level, String filename, boolean bufferedIO) {
		return getLogDao().getLogger(name, level, filename, bufferedIO);
	}

	public static Log getLogger(Class<?> clazz, Level level, String filename) {
		return getLogDao().getLogger(clazz, level, filename);
	}

	public static Log getLogger(Class<?> clazz, Level level, String filename, boolean bufferedIO) {
		return getLogDao().getLogger(clazz, level, filename, bufferedIO);
	}

	/**
	 * 禁止类初始化log.
	 */
	public static void disabledBeanLog() {
		getLogDao().disabledBeanLog();
	}

	/**
	 * 禁止公用包log.
	 */
	public static void disabledCommonsLog() {
		getLogDao().disabledCommonsLog();
	}
}
