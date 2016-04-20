package io.leopard.data4j.log;

import org.apache.commons.logging.Log;

public class ExternalAccessLog {
	/**
	 * UDB验证接口名称.
	 */
	public static final String UDB_INTERFACE_NAME = "udb";

	protected static Log externalAccessLogger = Log4jFactory.getExternalAccessLogger(ExternalAccessLog.class);

	// /**
	// * 外部接口访问耗时日志.
	// *
	// * @param millis
	// * 毫秒数
	// * @param interfaceName
	// * 接口名称,如:UDB
	// */
	// public static void debug(long millis, String interfaceName) {
	// String uri = MonitorContext.getRequestUri();
	// String proxyIp = MonitorContext.getProxyIp();
	// // if (StringUtils.isEmpty(uri)) {
	// //
	// // }
	// debug(proxyIp, millis, interfaceName, uri);
	// }

	/**
	 * 外部接口访问耗时日志.
	 * 
	 * @param millis
	 *            毫秒数
	 * @param interfaceName
	 *            接口名称,如:UDB
	 * @param uri
	 */
	public static void debug(String proxyIp, long millis, String interfaceName, String uri) {
		String message = proxyIp + " " + millis + " " + interfaceName + " " + uri;
		externalAccessLogger.debug(message);
	}

}
