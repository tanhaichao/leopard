package io.leopard.myjetty.webapp.classpath;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

public class ClassPathServiceYtestImpl extends AbstractClassPathService {

	@Override
	public void preConfigure(WebAppContext context, List<String> hostList, String war) {
		String path = "/data/ytest/workspace/lib/";
		File dir = new File(path);
		if (!dir.exists()) {
			return;
		}
		System.err.println("addYtestLibs:" + path);
		Resource lib = newResource(path);

		((WebAppClassLoader) context.getClassLoader()).addJars(lib);
		URL[] urls = ((WebAppClassLoader) context.getClassLoader()).getURLs();
		for (URL url : urls) {
			System.err.println("url:" + url.toString());
		}
	}

}
