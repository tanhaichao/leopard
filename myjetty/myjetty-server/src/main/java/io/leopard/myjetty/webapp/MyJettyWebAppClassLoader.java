package io.leopard.myjetty.webapp;

import java.io.IOException;

import org.eclipse.jetty.webapp.WebAppClassLoader;

public class MyJettyWebAppClassLoader extends WebAppClassLoader {

	public MyJettyWebAppClassLoader(Context context) throws IOException {
		super(context);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		return super.findClass(name);
	}

}
