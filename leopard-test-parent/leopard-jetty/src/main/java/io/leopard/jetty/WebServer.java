package io.leopard.jetty;

import java.net.BindException;

import org.eclipse.jetty.server.Server;

public interface WebServer {

	Server build(int port, String webApp, String contextPath) throws BindException;

}
