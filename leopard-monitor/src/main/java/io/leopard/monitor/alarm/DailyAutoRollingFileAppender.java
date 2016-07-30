package io.leopard.monitor.alarm;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import io.leopard.data4j.log.Logger;
import io.leopard.data4j.log.LoggerImpl;

//FIXME ahai 改成扩展接口实现
public class DailyAutoRollingFileAppender extends io.leopard.data4j.log.DailyAutoRollingFileAppender {

	private final RobotService robotService = AlarmModule.getRobotService();

	private Logger logger = new LoggerImpl();

	@Override
	public void append(LoggingEvent event) {
		super.append(event);

		if (logger.match(event.getLevel())) {
			event.getThrowableInformation().getThrowable();
		}

		if (true) {
			String message = event.getRenderedMessage();
			System.err.println("message:" + message);
		}
		// System.err.println("log append level:" + event.getLevel() + " name:" + super.getName() + " throw:" + event.getThrowableInformation());
		if (event.getLevel().isGreaterOrEqual(Level.ERROR)) {
			// System.err.println("super.getName():" + super.getName());
			if ("E1".equals(super.getName())) {
				this.alarm(event, "error");
			}
		}
		else if (event.getLevel().toInt() == Level.WARN.toInt()) {
			// System.err.println("super.getName():" + super.getName());
			if ("W1".equals(super.getName())) {
				this.alarm(event, "warn");
			}
		}
	}

	protected void alarm(LoggingEvent event, String level) {
		String message = event.getRenderedMessage();
		Throwable t = null;
		{
			ThrowableInformation throwableInformation = event.getThrowableInformation();
			if (throwableInformation != null) {
				t = throwableInformation.getThrowable();
			}
		}

		try {
			// this.sendToRobot(message, t);
			this.robotService.errorlog(level, message, t);
		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}

}
