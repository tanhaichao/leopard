package io.leopard.jetty.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebInfResolverImpl implements WebInfResolver {
	private List<WebInfResolver> webInfResolverList = new ArrayList<>();

	public WebInfResolverImpl() {
		Iterator<WebInfResolver> iterator = ServiceLoader.load(WebInfResolver.class).iterator();
		while (iterator.hasNext()) {
			WebInfResolver resolver = iterator.next();
			webInfResolverList.add(resolver);
		}
	}

	@Override
	public List<Resource> findClassDirs(WebAppContext context) throws Exception {
		List<Resource> result = new ArrayList<Resource>();
		for (WebInfResolver resolver : webInfResolverList) {
			List<Resource> list = resolver.findClassDirs(context);
			System.err.println("WebInfResolverImpl findClassDirs:" + resolver.getClass().getName() + " list:" + list);
			if (list != null) {
				result.addAll(list);
			}
		}
		return result;
	}

	@Override
	public List<Resource> findJars(WebAppContext context) throws Exception {
		List<Resource> result = new ArrayList<Resource>();
		for (WebInfResolver resolver : webInfResolverList) {
			List<Resource> list = resolver.findJars(context);
			// System.err.println("WebInfResolverImpl findJars:" + resolver.getClass().getName() + " list:" + list);
			if (list != null) {
				result.addAll(list);
			}
		}
		return result;
	}

}
