package io.leopard.jetty;

import org.eclipse.jetty.server.Server;

import io.leopard.jetty.impl.WebServerJettyImpl;

/**
 * 适用于servlet3.0+jetty8，本机开发使用。
 */
public class JettyServer {

	public static Server start() throws Exception {
		// org.objectweb.asm.commons.EmptyVisitor ddd;
		return start(80);
	}

	public static Server start(String webApp) throws Exception {
		return start(webApp, "/", 80);
	}

	public static Server start(int port) throws Exception {
		return start("/", port);
	}

	public static Server start(String contextPath, int port) throws Exception {
		return start("src/main/webapp", contextPath, port);
	}

	public static Server start(String webApp, String contextPath, int port) throws Exception {
		Server server = new WebServerJettyImpl().build(port, webApp, contextPath);
		server.start();
		return server;
	}

}
