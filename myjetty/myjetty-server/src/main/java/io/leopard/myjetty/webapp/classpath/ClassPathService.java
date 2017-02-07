package io.leopard.myjetty.webapp.classpath;

import java.util.List;

import org.eclipse.jetty.webapp.WebAppContext;

public interface ClassPathService {

	void preConfigure(WebAppContext context, List<String> hostList, String war);
}
