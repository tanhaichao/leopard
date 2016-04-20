package io.leopard.data4j.log;

import org.apache.commons.logging.Log;

public class DataSourceLog {
	protected static Log dataSourceLogger = Log4jFactory.getDataSourceLogger(DataSourceLog.class);

	public static void debug(String type, String message) {
		// if ("connect".equals(type)) {
		// System.err.println(type + " " + message);
		dataSourceLogger.debug(type + " " + message);
		// }
	}
}
