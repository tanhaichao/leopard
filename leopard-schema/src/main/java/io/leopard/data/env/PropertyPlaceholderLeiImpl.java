package io.leopard.data.env;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
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
			else {
				dsnResource = decrypt(dsnResource);
			}
		}
		return new Resource[] { dsnResource };
	}

	/**
	 * 解密.
	 * 
	 * @param resource
	 * @return
	 */
	protected Resource decrypt(Resource resource) {
		String content;
		try {
			InputStream input = resource.getInputStream();
			content = IOUtils.toString(input);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		System.out.println("content:" + content);
		return resource;
	}
}
