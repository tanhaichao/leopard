package io.leopard.jetty.configuration;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class EmbedWebXmlConfiguration extends WebXmlConfiguration {
	protected static final Logger LOG = Log.getLogger(EmbedWebXmlConfiguration.class);

	@Override
	public void preConfigure(WebAppContext context) throws Exception {
		// LOG.info("preConfigure");
		super.preConfigure(context);
	}

	// @Override
	// public void configure(WebAppContext context) throws Exception {
	// super.configure(context);
	// }
}
