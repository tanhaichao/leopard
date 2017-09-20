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
			List<Resource> list = loader.findJars(context);
			System.err.println("ResourceLoaderImpl loader:" + loader.getClass().getName() + " list:" + list);
			if (list != null) {
				result.addAll(list);
			}
		}
		return result;
	}

}
