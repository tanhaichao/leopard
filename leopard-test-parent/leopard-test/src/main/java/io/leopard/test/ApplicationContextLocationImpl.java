package io.leopard.test;

import java.util.ArrayList;
import java.util.List;

public class ApplicationContextLocationImpl implements ApplicationContextLocation {

	private List<ApplicationContextLocation> list = new ArrayList<ApplicationContextLocation>();

	public ApplicationContextLocationImpl() {
		list.add(new ApplicationContextLocationFirstImpl());
		list.add(new ApplicationContextLocationModuleImpl());
	}

	@Override
	public String[] get() {
		for (ApplicationContextLocation location : list) {
			String[] locations = location.get();
			if (locations != null) {
				return locations;
			}
		}
		throw new RuntimeException("找不到ApplicationContext配置.");
	}

}
