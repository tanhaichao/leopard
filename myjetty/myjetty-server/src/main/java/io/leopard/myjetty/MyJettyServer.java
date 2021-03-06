package io.leopard.myjetty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

import io.leopard.myjetty.webapp.RestartingHandler;
import io.leopard.myjetty.webapp.WebappService;
import io.leopard.myjetty.webapp.WebappServiceImpl;

public class MyJettyServer implements WebServer {
	protected static Log logger = LoggerConstant.getJettyLogger(MyJettyServer.class);

	private Server server;

	private WebappService webappService;

	public WebappService getWebappService() {
		return webappService;
	}

	protected List<ServerConnector> listPort(int port) {
		List<ServerConnector> list = new ArrayList<ServerConnector>();
		int defaultPort = this.getDefaultPort(port);
		if (defaultPort != port) {
			ServerConnector connector = createServerConnector(this.server);
			connector.setPort(defaultPort);
			list.add(connector);
		}
		{
			ServerConnector connector = createServerConnector(this.server);
			connector.setPort(port);
			list.add(connector);
		}
		// connector0.setMaxIdleTime(30000);
		// connector0.setRequestHeaderSize(8192);
		return list;
	}

	protected ServerConnector createServerConnector(Server server) {
		HttpConfiguration httpConfig = new HttpConfiguration();
		// http_config.setSecureScheme("https");
		// http_config.setSecurePort(8443);
		// http_config.setOutputBufferSize(32768);
		// http_config.setRequestHeaderSize(8192);
		// http_config.setResponseHeaderSize(8192);
		httpConfig.setSendServerVersion(false);
		httpConfig.setSendDateHeader(false);
		return new ServerConnector(server, new HttpConnectionFactory(httpConfig));
	}

	protected int getDefaultPort(int port) {
		if (port != 80) {
			boolean useDefaultPort = "true".equals(System.getenv("useDefaultPort"));
			if (useDefaultPort) {
				return 80;
			}
		}
		return port;
	}

	public MyJettyServer() {
		org.eclipse.jetty.util.log.Log.setLog(null);
		this.server = new Server();
		this.webappService = WebappServiceImpl.getInstance();
	}

	public Server start(int port) throws Exception {
		System.setProperty("spring.profiles.active", "jetty");

		List<ServerConnector> connectorList = this.listPort(port);
		Connector[] connectors = new Connector[connectorList.size()];
		connectorList.toArray(connectors);
		server.setConnectors(connectors);

		HandlerCollection handlers = new HandlerCollection();

		// webappService.addHandler(new WorkbenchHandler(server));//TODO

		handlers.addHandler(webappService.getContextHandlerCollection());
		new HandlerServiceImpl().execute(handlers);

		handlers.addHandler(new RestartingHandler());

		server.setHandler(handlers);

		System.out.println("started");
		logger.info("jetty started");
		server.start();

		// WebappController.getInstance().setWebappService(webappService);//TODO

		return server;
	}

	@Override
	public void join() throws InterruptedException {
		server.join();
	}

	@Override
	public void stop() throws Exception {
		// server.setGracefulShutdown(3000);
		server.setStopAtShutdown(true);
		server.stop();
	}

	@Override
	public WebAppContext startWebapp(String war) throws Exception {
		return webappService.start(war);
	}

	@Override
	public WebAppContext startWebapp(String war, List<String> hostList) throws Exception {
		return webappService.start(war, hostList);
	}

	public Server getServer() {
		return server;
	}

	public static void main(String[] args) throws Exception {
		// mvn exec:java -Dexec.mainClass="io.leopard.myjetty.JetyServer"
		MyJettyServer jetyServer = new MyJettyServer();
		jetyServer.start(80);
		// WebappServiceImpl.getInstance().setDefaultProjectId("zhongcao");

		// jetyServer.startWebapp("zhongcao");
	}
}
