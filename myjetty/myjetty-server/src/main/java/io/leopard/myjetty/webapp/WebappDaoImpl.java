package io.leopard.myjetty.webapp;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;

import io.leopard.jetty.handler.HostResourceHandler;
import io.leopard.jetty.handler.LeopardWebAppClassLoader;
import io.leopard.myjetty.LoggerConstant;

public class WebappDaoImpl implements WebappDao {
	protected static Log logger = LoggerConstant.getJettyLogger(WebappServiceImpl.class);

	private Map<String, WebAppContext> contextMap = new ConcurrentHashMap<String, WebAppContext>();

	protected Handler getHandlerList(List<String> hostList) {
		// jettyServer.getWebappService().addHandler(new HostResourceHandler("fshop.leopard.io", "/data/src/fshop_html/"));
		// jettyServer.getWebappService().addHandler(new HostResourceHandler("ftrade.leopard.io", "/data/src/ftrade_html/"));
		if (hostList.contains("fshop.leopard.io")) {
			if (false) {
				return new HostResourceHandler("fshop.leopard.io", "/data/src/fshop_html/");
			}
			HandlerList handlerList = new HandlerList();
			handlerList.addHandler(new HostResourceHandler("fshop.leopard.io", "/data/src/fshop_html/"));
			handlerList.addHandler(new HostResourceHandler("ftrade.leopard.io", "/data/src/ftrade_html/"));
			try {
				handlerList.start();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return handlerList;
		}
		return null;
	}

	@Override
	public WebAppContext add(String war, List<String> hostList) throws Exception {
		if (true) {
			if (contextMap.containsKey(war)) {
				throw new RuntimeException("webapp[" + war + "]已添加.");
			}
		}

		Handler handlerList = null;// this.getHandlerList(hostList);

		MyJettyWebAppContext webapp = new MyJettyWebAppContext(hostList, war) {
			@Override
			public void doScope(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
				// System.out.println("WebappDaoImpl doScope start target:" + target + " serverName:" + request.getServerName() + " isHandled:" + baseRequest.isHandled() + " handler:" + handler
				// + " hostList:" + hostList);
				if (handlerList != null) {
					handlerList.handle(target, baseRequest, request, response);
					if (response.isCommitted() || baseRequest.isHandled()) {
						return;
					}
				}
				// System.out.println("WebappDaoImpl doScope end target:" + target + " serverName:" + request.getServerName() + " isHandled:" + baseRequest.isHandled() + " handler:" + handler
				// + " hostList:" + hostList);
				super.doScope(target, baseRequest, request, response);
			}
		};

		// WebAppContext webapp = new WebAppContext();

		if (true) {
			LeopardWebAppClassLoader classLoader = new LeopardWebAppClassLoader(webapp);
			webapp.setClassLoader(classLoader);
			webapp.setParentLoaderPriority(false);
			logger.info("classLoader:" + classLoader + " hashCode:" + classLoader.hashCode());
		}
		// contextHandlerCollection.addHandler(webapp);
		contextMap.put(war, webapp);
		// webapp.start();
		return webapp;
	}

	@Override
	public WebAppContext get(String war) {
		return contextMap.get(war);
	}

	@Override
	public WebAppContext remove(String war) {
		return contextMap.remove(war);
		// return webapp;
	}

}
