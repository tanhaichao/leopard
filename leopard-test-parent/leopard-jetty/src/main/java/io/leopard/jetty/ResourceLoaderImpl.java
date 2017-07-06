package io.leopard.jetty;

import java.util.List;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class ResourceLoaderImpl implements IResourceLoader {

	@Override
	public List<Resource> findJars(WebAppContext context) throws Exception {
		return null;
	}

}
