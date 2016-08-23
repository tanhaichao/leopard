package io.leopard.data.env;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class AppInitializerImpl implements AppInitializer {

	private List<AppInitializer> list = new ArrayList<AppInitializer>();

	public AppInitializerImpl() {
		Iterator<AppInitializer> iterator = ServiceLoader.load(AppInitializer.class).iterator();
		while (iterator.hasNext()) {
			AppInitializer element = iterator.next();
			list.add(element);
		}
	}

	@Override
	public void init() {
		for (AppInitializer initializer : list) {
			initializer.init();
		}
	}
}
