package io.leopard.jetty.test;

import io.leopard.httpnb.Httpnb;
import io.leopard.jetty.JettyServer;

import org.eclipse.jetty.server.Server;

public class JettyTester {

	public static Server start() throws Exception {
		return JettyServer.start("src/test/webapp");
	}

	public static Server start(String webApp) throws Exception {
		return JettyServer.start(webApp);
	}

	public static String doGet(String url) {
		Server server;
		try {
			server = start();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		String result = Httpnb.doGet(url);
		try {
			server.stop();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}
}
