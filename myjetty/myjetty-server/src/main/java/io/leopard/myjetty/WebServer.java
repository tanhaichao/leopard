package io.leopard.myjetty;

import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public interface WebServer {
	public Server start(int port) throws Exception;

	public void stop() throws Exception;

	WebAppContext startWebapp(String war) throws Exception;

	WebAppContext startWebapp(String war, List<String> hostList) throws Exception;

	void join() throws InterruptedException;

}
