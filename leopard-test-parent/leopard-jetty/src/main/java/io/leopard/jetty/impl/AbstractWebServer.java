package io.leopard.jetty.impl;

import io.leopard.jetty.WebServer;

import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class AbstractWebServer implements WebServer {

	protected void checkOpened(int port) throws BindException {
		try {
			Socket s = new Socket();
			s.bind(new InetSocketAddress("0.0.0.0", port));
			s.close();
		}
		catch (java.net.BindException e) {
			String message = e.getMessage();
			if ("权限不够".equals(message) || "Permission denied".equals(message)) {
				throw new BindException("您无权限绑定" + port + "端口");
			}
			e.printStackTrace();
			throw new BindException("端口[" + port + "]已被占用.");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new BindException("端口[" + port + "]已被占用.");
		}
	}

	/**
	 * 智能选择port.
	 * 
	 * @param port
	 * @return
	 * @throws BindException
	 */
	protected int getAutoPort(int port) throws BindException {
		if (System.getProperty("os.name").startsWith("Mac")) {
			if (port == 80) {
				port = 8080;
			}
			return port;
		}
		try {
			checkOpened(port);
		}
		catch (BindException e) {
			if (port == 80) {
				checkOpened(8080);
				port = 8080;
			}
			else {
				throw e;
			}
		}
		return port;
	}
}
