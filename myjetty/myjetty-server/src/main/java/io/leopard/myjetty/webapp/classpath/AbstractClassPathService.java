package io.leopard.myjetty.webapp.classpath;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;

public abstract class AbstractClassPathService implements ClassPathService {

	public static final String METAINF_RESOURCES = WebInfConfiguration.RESOURCE_DIRS;

	private void addResource(WebAppContext context, Resource jar) {
		@SuppressWarnings("unchecked")
		Set<Resource> list = (Set<Resource>) context.getAttribute(METAINF_RESOURCES);
		if (list == null) {
			list = new LinkedHashSet<Resource>();
			context.setAttribute(METAINF_RESOURCES, list);
		}
		if (!list.contains(jar)) {
			list.add(jar);
		}
	}

	protected void addClassPath(WebAppContext context, Resource classes) {
		try {
			((WebAppClassLoader) context.getClassLoader()).addClassPath(classes);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		this.addResource(context, classes);
	}

	protected Resource newResource(String path) {
		try {
			return Resource.newResource(path);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}
}
