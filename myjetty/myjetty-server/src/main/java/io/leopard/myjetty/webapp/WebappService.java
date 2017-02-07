package io.leopard.myjetty.webapp;

import java.util.List;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

public interface WebappService {

	ContextHandlerCollection getContextHandlerCollection();

	WebAppContext start(String war) throws Exception;

	WebAppContext start(String war, List<String> hostList) throws Exception;

	WebAppContext getWebapp(String war);

	WebAppContext stop(String war) throws Exception;

	void addHandler(Handler handler);

	WebAppContext remove(String war);

	Directory addDirectory(String war, List<String> hostList);

}
