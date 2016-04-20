package io.leopard.data4j.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogDaoInterfaceImpl implements LogDao {

	@Override
	public Log getLogger(String name, Level level, String filename) {
		return LogFactory.getLog(name);
	}

	@Override
	public Log getLogger(String name, Level level, String filename, boolean bufferedIO) {
		return LogFactory.getLog(name);
	}

	@Override
	public Log getLogger(Class<?> clazz, Level level, String filename) {
		return LogFactory.getLog(clazz);
	}

	@Override
	public Log getLogger(Class<?> clazz, Level level, String filename, boolean bufferedIO) {
		return LogFactory.getLog(clazz);
	}

	@Override
	public void disabledBeanLog() {

	}

	@Override
	public void disabledCommonsLog() {

	}

}
