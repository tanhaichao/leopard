package io.leopard.jetty.impl;

import java.util.Iterator;
import java.util.ServiceLoader;

import io.leopard.jetty.ServerInitializer;

public class ServerInitializerImpl implements ServerInitializer {

	public ServerInitializerImpl() {
		Iterator<ServerInitializer> iterator = ServiceLoader.load(ServerInitializer.class).iterator();
		while (iterator.hasNext()) {
			ServerInitializer initializer = iterator.next();
			initializer.run();
		}
	}

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
