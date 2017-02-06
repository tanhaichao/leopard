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

/**
 * 解决jetty自带的WebInfConfiguration只扫描WEB-INF/lib的问题，maven编译后的目录为target，不符合其规则
 * 
 * @author dw_lixuan
 * @version version1
 * @since 2013-02-18
 */
public class EmbedWebInfConfiguration extends WebInfConfiguration {
	protected static final Logger LOG = Log.getLogger(EmbedWebInfConfiguration.class);

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
				// System.err.println("_url:"+_url);
				list.add(Resource.newResource(_url));
			}
		}

		return list;
	}

	// protected void changeClassLoader(WebAppContext webContext, String jarFile) throws IOException {
	// // System.err.println("start test");
	// // System.err.println("webinf:" + webContext.getBaseResource());
	// ClassLoader classLoader = webContext.getClassLoader();
	//
	// URL[] urls = new URL[1];
	// urls[0] = new File(jarFile).toURI().toURL();
	// // urls[0] = new File("/work/news/leopard/leopard-web/src/main/resources/").toURI().toURL();
	// URLClassLoader urlClassLoader = new URLClassLoader(urls, classLoader);
	// webContext.setClassLoader(urlClassLoader);
	// }
}
