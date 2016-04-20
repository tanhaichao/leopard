package io.leopard.data4j.log;

import io.leopard.data4j.log.config.LogDirLeiImpl;

import org.apache.commons.logging.Log;

public class Log4jFactory {
	public static Log getTimeAvgLogger(Class<?> clazz) {
		return Log4jUtil.getLogger("TIMEAVG." + clazz.getName(), Level.DEBUG, LogDirLeiImpl.getLogDir() + "/time.avg.log", false);
	}

	// public static Log getErrorLogger(Class<?> clazz) {
	// Logger logger = Logger.getLogger(clazz);
	//
	// org.apache.log4j.Level log4jLevel = LogDaoLog4jImpl.toLog4jLevel(level);
	//
	//
	// String conversionPattern = "%d{yyyy-MM-dd HH:mm:ss} - %m%n";
	//
	// org.apache.log4j.PatternLayout layout = new PatternLayout();
	// layout.setConversionPattern(conversionPattern);
	//
	// DailyAutoRollingFileAppender appender = new DailyAutoRollingFileAppender();
	// // DailyRollingFileAppender newAppender = new DailyRollingFileAppender();
	// appender.setThreshold(log4jLevel);
	// appender.setLayout(layout);
	// appender.setDatePattern(".yyyyMMdd");
	// appender.setFile(filename);
	// appender.setBufferSize(8192);
	// appender.setBufferedIO(bufferedIO);
	// appender.activateOptions();
	//
	// addAppender(logger, level, filename, bufferedIO);
	//
	// return new Log4jAdapter(logger);
	// }

	public static Log getTimerLogger(Class<?> clazz) {
		return Log4jUtil.getLogger("TIMERLOG." + clazz.getName(), Level.DEBUG, LogDirLeiImpl.getLogDir() + "/timer.log", false);
	}

	public static Log getAlarmLogger(Class<?> clazz) {
		return Log4jUtil.getLogger("ALARMLOG." + clazz.getName(), Level.DEBUG, LogDirLeiImpl.getLogDir() + "/alarm.log", false);
	}

	public static Log getExternalAccessLogger(Class<?> clazz) {
		return Log4jUtil.getLogger("EXTERNALACCESSLOG." + clazz.getName(), Level.DEBUG, LogDirLeiImpl.getLogDir() + "/external.access.log", false);
	}

	public static Log getDataSourceLogger(Class<?> clazz) {
		return Log4jUtil.getLogger("DATASOURCELOG." + clazz.getName(), Level.DEBUG, LogDirLeiImpl.getLogDir() + "/datasource.log", false);
	}

	public static Log getSlowLogger(Class<?> clazz) {
		return Log4jUtil.getLogger("SLOWLOG." + clazz.getName(), Level.DEBUG, LogDirLeiImpl.getLogDir() + "/slow.log", false);
	}

	public static Log getWebserviceLogger(Class<?> clazz) {
		return Log4jUtil.getLogger("WEBSERVICELOG." + clazz.getName(), Level.DEBUG, LogDirLeiImpl.getLogDir() + "/webservice.log");
	}

}
