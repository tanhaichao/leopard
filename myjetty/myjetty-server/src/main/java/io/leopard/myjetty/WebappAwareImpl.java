package io.leopard.myjetty;

import org.eclipse.jetty.webapp.WebAppContext;

public class WebappAwareImpl implements WebappAware {

	private static WebappAware instance = new WebappAwareImpl();

	public static WebappAware getInstance() {
		return instance;
	}

	public static void setInstance(WebappAware instance) {
		WebappAwareImpl.instance = instance;
	}

	@Override
	public void preConfigure(WebAppContext context) throws Exception {
		instance.preConfigure(context);
	}

	@Override
	public void configure(WebAppContext context) throws Exception {
		instance.configure(context);
	}

}
