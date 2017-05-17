package io.leopard.myjetty;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServerInitializerImpl implements ServerInitializer {

	public ServerInitializerImpl() {
		System.out.println("new ServerInitializerImpl()	");
		Iterator<ServerInitializer> iterator = ServiceLoader.load(ServerInitializer.class).iterator();
		while (iterator.hasNext()) {
			ServerInitializer initializer = iterator.next();
			initializer.run();
		}
	}

	@Override
	public void run() {

	}

}
