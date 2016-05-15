package io.leopard.data.env;

import org.springframework.core.io.Resource;

public class PropertyPlaceholderLeiImpl implements PropertyPlaceholderLei {

	private PropertyPlaceholderLei classpathImpl = new PropertyPlaceholderLeiClasspathImpl();

	@Override
	public Resource[] getResources(String env) {
		Resource[] resources = classpathImpl.getResources(env);
		return resources;
	}

}
