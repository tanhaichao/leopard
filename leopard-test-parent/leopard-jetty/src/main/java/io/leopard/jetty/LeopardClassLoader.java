package io.leopard.jetty;

import java.io.IOException;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.webapp.WebAppClassLoader;

public class LeopardClassLoader extends WebAppClassLoader {
	protected static final Logger logger = Log.getLogger(LeopardClassLoader.class);

	public LeopardClassLoader(Context context) throws IOException {
		super(context);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// logger.info("findClass:" + name);
		return super.findClass(name);
	}

}
