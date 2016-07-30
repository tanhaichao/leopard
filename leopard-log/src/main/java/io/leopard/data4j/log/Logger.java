package io.leopard.data4j.log;

import org.apache.log4j.spi.LoggingEvent;

/**
 * 日志
 * 
 * @author 谭海潮
 *
 */
public interface Logger {

	void log(LoggingEvent event);
}
