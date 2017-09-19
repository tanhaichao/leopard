package io.leopard.jetty.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class ResourceLoaderImpl implements IResourceLoader {

	@Override
	public List<Resource> findJars(WebAppContext context) throws Exception {
		List<Resource> result = new ArrayList<Resource>();
		Iterator<IResourceLoader> iterator = ServiceLoader.load(IResourceLoader.class).iterator();
		while (iterator.hasNext()) {
			IResourceLoader loader = iterator.next();
			// System.err.println("loader:" + loader.getClass().getName());
			List<Resource> list = loader.findJars(context);
			if (list != null) {
				result.addAll(list);
			}
		}
		return result;
	}

}
