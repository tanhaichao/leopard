package io.leopard.jetty.impl;

import io.leopard.jetty.ServerInitializer;

public class ServerInitializerImpl implements ServerInitializer {

	@Override
	public void run() {
		String className = "io.leopard.javahost.AutoUnitRunnable";
		try {
			Runnable runnable = (Runnable) Class.forName(className).newInstance();
			runnable.run();
		}
		catch (RuntimeException e) {
			// System.err.println("init hosts error:" + e.toString());
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// e.printStackTrace();
		}
		System.setProperty("spring.profiles.active", "dev");

	}

}
