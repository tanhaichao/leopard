package io.leopard.myjetty;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServerInitializerImpl implements ServerInitializer {

	public ServerInitializerImpl() {
		Iterator<ServerInitializer> iterator = ServiceLoader.load(ServerInitializer.class).iterator();
		System.out.println("new ServerInitializerImpl()	");
		while (iterator.hasNext()) {
			ServerInitializer initializer = iterator.next();
			System.out.println("initializer:" + initializer.getClass().getName());
			initializer.run();
		}
	}

	@Override
	public void run() {

	}

}
