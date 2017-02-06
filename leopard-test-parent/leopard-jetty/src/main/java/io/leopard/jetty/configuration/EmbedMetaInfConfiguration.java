package io.leopard.jetty.configuration;

import java.io.IOException;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbedMetaInfConfiguration extends MetaInfConfiguration {
	protected static final Logger LOG = Log.getLogger(EmbedMetaInfConfiguration.class);

	protected void addTldResource(final WebAppContext context, Resource resource, String name) throws IOException {
		Resource tldResource = resource.getResource(name);
		if (tldResource.exists()) {
			// addResource(context, METAINF_TLDS, tldResource);
		}
	}

	protected void addFolderResource(final WebAppContext context) throws IOException {
		for (Resource resource : context.getMetaData().getWebInfJars()) {
			String url = resource.toString();
			if (!isClassesDir(url)) {
				continue;
			}
			{
				Resource fragmentResource = resource.getResource("META-INF/web-fragment.xml");
				if (fragmentResource.exists()) {
					// addResource(context, METAINF_FRAGMENTS, resource);
				}
			}

			this.addTldResource(context, resource, "META-INF/fnx.tld");
			this.addTldResource(context, resource, "META-INF/dw.tld");
		}
	}

	protected boolean isClassesDir(String url) {
		return url.endsWith("/classes/") || url.endsWith("/classes");
	}

	@Override
	public void preConfigure(final WebAppContext context) throws Exception {
		// LOG.info("preConfigure");
		// this.addFolderResource(context);
		super.preConfigure(context);
	}

}
