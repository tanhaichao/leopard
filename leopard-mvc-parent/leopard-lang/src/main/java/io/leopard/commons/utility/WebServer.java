package io.leopard.commons.utility;

public class WebServer {

	public static boolean isWebServer() {
		{
			boolean isWebServer = Resin.isResin();
			if (isWebServer) {
				return isWebServer;
			}
		}
		// {
		// boolean isWebServer = JettyUtil.isJetty();
		// if (isWebServer) {
		// return isWebServer;
		// }
		// }
		return false;
	}

	/**
	 * // * 判断是否为Jetty // * // * @return boolean //
	 */
	// public static boolean isJetty() {
	// String value = System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
	// return "jetty".equals(value);
	// }
}
