package io.leopard.jetty.configuration;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;

import io.leopard.jetty.handler.ResourceLoaderImpl;

/**
 * 解决jetty自带的WebInfConfiguration只扫描WEB-INF/lib的问题，maven编译后的目录为target，不符合其规则
 */
public class EmbedWebInfConfiguration extends WebInfConfiguration {
	protected Logger logger = Log.getLogger(EmbedWebInfConfiguration.class);

	@Override
	protected List<Resource> findJars(WebAppContext context) throws Exception {
		List<Resource> list = super.findJars(context);
		if (list == null) {
			list = new ArrayList<Resource>();
		}
		ClassLoader aLoader = getClass().getClassLoader();
		if (aLoader instanceof URLClassLoader) {
			URL[] _urls = ((URLClassLoader) aLoader).getURLs();
			for (URL _url : _urls) {
				// System.err.println("EmbedWebInfConfiguration url:" + _url.getPath());
				list.add(Resource.newResource(_url));
			}
		}
		if (true) {
			List<Resource> extendResourceList = new ResourceLoaderImpl().findJars(context);
			// System.err.println("extendResourceList:" + extendResourceList);
			if (extendResourceList != null) {
				list.addAll(extendResourceList);
			}
		}
		return list;
	}

}
