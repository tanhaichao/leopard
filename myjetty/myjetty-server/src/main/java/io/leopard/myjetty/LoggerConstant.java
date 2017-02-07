package io.leopard.myjetty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerConstant {

	public static Log getJettyLogger(Class<?> clazz) {
		// return Log4jUtil.getLogger("JETTYLOG." + clazz.getName(), Level.DEBUG, "/data2/log/resin/jetty.log", false);

		return LogFactory.getLog(clazz);
	}
}
