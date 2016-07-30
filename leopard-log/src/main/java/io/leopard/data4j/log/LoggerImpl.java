package io.leopard.data4j.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.apache.log4j.spi.LoggingEvent;

public class LoggerImpl implements Logger {

	private List<Logger> list = new ArrayList<Logger>();

	public LoggerImpl() {
		Iterator<Logger> iterator = ServiceLoader.load(Logger.class).iterator();
		while (iterator.hasNext()) {
			Logger logger = iterator.next();
			list.add(logger);
		}
	}

	@Override
	public void log(LoggingEvent event) {
		for (Logger logger : list) {
			logger.log(event);
		}
	}

}
