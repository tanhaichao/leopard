package io.leopard.data4j.env;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertyPlaceholderLeiImpl implements PropertyPlaceholderLei {

	@Override
	public Resource[] getResources(String env) {
		Resource dsnResource = new ClassPathResource("/" + env + "/app.properties");
		if (!dsnResource.exists()) {
			dsnResource = new ClassPathResource("/app.properties");
			if (!dsnResource.exists()) {
				System.err.println("/app.properties不存在");
			}
		}
		// System.err.println("getResources env:" + env);
		return new Resource[] { dsnResource };
	}

}
