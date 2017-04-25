package io.leopard.jdbc.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JdbcLoggerImpl implements JdbcLogger {

	private static final JdbcLoggerImpl instance = new JdbcLoggerImpl();

	public static JdbcLoggerImpl getInstance() {
		return instance;
	}

	private JdbcLogger jdbcLogger = null;

	protected Log logger = LogFactory.getLog(this.getClass());

	public static void setStyle(JdbcLoggerStyle style) {
		if (style == JdbcLoggerStyle.ARGUMENT_PARSE) {
			instance.jdbcLogger = new ArgumentParseJdbcLogger();
		}
		else if (style == JdbcLoggerStyle.NO_ARGUMENT_PARSE) {
			instance.jdbcLogger = new NoArgumentParseJdbcLogger();
		}
		else {
			instance.jdbcLogger = new ArgumentParseJdbcLogger();
		}
	}

	@Override
	public void update(int updatedCount, String sql, Object[] args) {
		jdbcLogger.update(updatedCount, sql, args);
	}

}
