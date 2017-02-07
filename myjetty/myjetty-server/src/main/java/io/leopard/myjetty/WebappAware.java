package io.leopard.myjetty;

import org.eclipse.jetty.webapp.WebAppContext;

public interface WebappAware {
	void preConfigure(WebAppContext context) throws Exception;

	void configure(WebAppContext context) throws Exception;
}
