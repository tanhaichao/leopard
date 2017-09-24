package io.leopard.jetty.handler;

import java.io.IOException;
import java.net.URL;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.webapp.WebAppClassLoader;

public class LeopardWebAppClassLoader extends WebAppClassLoader {
	protected static final Logger logger = Log.getLogger(LeopardWebAppClassLoader.class);

	public LeopardWebAppClassLoader(Context context) throws IOException {
		super(context);
	}

	@Override
	public URL getResource(String name) {
		URL oUrl = super.getResource(name);
		// if (name.endsWith(".xml")) {
		// if (oUrl != null) {
		// logger.info("getResource:" + name + " url:" + oUrl.toString());
		// }
		// }
		return oUrl;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// logger.info("findClass:" + name);
		return super.findClass(name);
	}

}
