package io.leopard.data4j.log;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

public class Log4jAdapter implements Log {
	private Logger logger;

	public Log4jAdapter(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isEnabledFor(org.apache.log4j.Level.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return logger.isEnabledFor(org.apache.log4j.Level.FATAL);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isEnabledFor(org.apache.log4j.Level.WARN);
	}

	@Override
	public void trace(Object message) {
		logger.trace(message);
	}

	@Override
	public void trace(Object message, Throwable t) {
		logger.trace(message, t);
	}

	@Override
	public void debug(Object message) {
		logger.debug(message);
	}

	@Override
	public void debug(Object message, Throwable t) {
		logger.debug(message, t);
	}

	@Override
	public void info(Object message) {
		logger.info(message);
	}

	@Override
	public void info(Object message, Throwable t) {
		logger.info(message, t);
	}

	@Override
	public void warn(Object message) {
		logger.warn(message);
	}

	@Override
	public void warn(Object message, Throwable t) {
		logger.warn(message, t);
	}

	@Override
	public void error(Object message) {
		logger.error(message);
	}

	@Override
	public void error(Object message, Throwable t) {
		logger.error(message, t);
	}

	@Override
	public void fatal(Object message) {
		logger.fatal(message);
	}

	@Override
	public void fatal(Object message, Throwable t) {
		logger.fatal(message, t);
	}

}
