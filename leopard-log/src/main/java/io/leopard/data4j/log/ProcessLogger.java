package io.leopard.data4j.log;

import io.leopard.data4j.log.config.LogDirLeiImpl;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;

public class ProcessLogger implements Log {
	protected Log processLogger;

	public ProcessLogger(Class<?> clazz) {
		// processLogger = LogFactory.getLog("PROCESSLOG." + clazz.getName());
		processLogger = Log4jUtil.getLogger("PROCESSLOG." + clazz.getName(), Level.DEBUG, LogDirLeiImpl.getLogDir() + "/process.log", false);
	}

	public static ProcessLogger newInstance(Class<?> clazz) {
		ProcessLogger processLogger = new ProcessLogger(clazz);
		return processLogger;
	}

	public void info(int size, long time, String message) {
		long avg = this.avg(size, time);
		processLogger.info(message + " size:" + format(size) + " time:" + format(time) + "ms" + " avg:" + format(avg));
	}

	public void debug(int size, long time, String message) {
		long avg = this.avg(size, time);
		processLogger.debug(message + " size:" + format(size) + " time:" + format(time) + "ms" + " avg:" + format(avg));
		// System.out.println(message + " size:" + format(size) + " time:" +
		// format(time) + "ms" + " avg:" + format(avg));
	}

	protected long avg(int size, long time) {
		if (time <= 0) {
			return size;
		}
		else {
			long size2 = size * 1000L;
			long avg = size2 / time;
			return avg;
		}
	}

	protected String format(double num) {
		return new DecimalFormat(",###").format(num);
	}

	@Override
	public boolean isDebugEnabled() {
		return this.processLogger.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return this.processLogger.isErrorEnabled();
	}

	@Override
	public boolean isFatalEnabled() {
		return this.processLogger.isFatalEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return this.processLogger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return this.processLogger.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return this.processLogger.isWarnEnabled();
	}

	@Override
	public void trace(Object message) {
		processLogger.trace(message);
	}

	@Override
	public void trace(Object message, Throwable t) {
		processLogger.trace(message, t);
	}

	@Override
	public void debug(Object message) {
		processLogger.info(message);
	}

	@Override
	public void debug(Object message, Throwable t) {
		processLogger.debug(message, t);
	}

	@Override
	public void info(Object message) {
		processLogger.info(message);
	}

	@Override
	public void info(Object message, Throwable t) {
		processLogger.info(message, t);
	}

	@Override
	public void warn(Object message) {
		processLogger.warn(message);
	}

	@Override
	public void warn(Object message, Throwable t) {
		processLogger.warn(message, t);
	}

	@Override
	public void error(Object message) {
		processLogger.error(message);
	}

	@Override
	public void error(Object message, Throwable t) {
		processLogger.error(message, t);
	}

	@Override
	public void fatal(Object message) {
		processLogger.fatal(message);
	}

	@Override
	public void fatal(Object message, Throwable t) {
		processLogger.fatal(message, t);
	}
}
