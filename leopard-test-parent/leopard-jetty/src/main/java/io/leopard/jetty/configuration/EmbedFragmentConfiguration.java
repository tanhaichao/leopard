package io.leopard.jetty.configuration;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbedFragmentConfiguration extends FragmentConfiguration {
	protected static final Logger LOG = Log.getLogger(EmbedFragmentConfiguration.class);

	@Override
	public void preConfigure(WebAppContext context) throws Exception {
		// LOG.info("preConfigure");
		super.preConfigure(context);
	}
	// public void findWebFragments(final WebAppContext context, final MetaData metaData) throws Exception {
	// super.findWebFragments(context, metaData);
	//
	// @SuppressWarnings("unchecked")
	// Map<Resource, Resource> frags = (Map<Resource, Resource>) context.getAttribute(FRAGMENT_RESOURCES);
	// if (frags != null) {
	// for (Resource key : frags.keySet()) {
	// System.err.println("key:" + key);
	// }
	// }
	// }
}
