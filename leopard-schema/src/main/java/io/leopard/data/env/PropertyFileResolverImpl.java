package io.leopard.data.env;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertyFileResolverImpl implements PropertyFileResolver {

	@Override
	public Resource[] getResources(String env) throws IOException {
		Resource dsnResource = new ClassPathResource("/" + env + "/app.properties");
		if (!dsnResource.exists()) {
			dsnResource = new ClassPathResource("/app.properties");
			if (!dsnResource.exists()) {
				throw new FileNotFoundException();
			}
		}
		return new Resource[] { dsnResource };
	}

}
