package io.leopard.data4j.env;

import java.io.IOException;
import java.net.URL;

import org.springframework.core.io.ClassPathResource;

public class ClassLoaderUtil {

	public static String getCurrentPath() {
		URL url = ClassLoader.getSystemResource(".");
		if (url == null) {
			return getPathByLog4j();
		}
		String path = url.toString();
		return path;
	}

	protected static String getPathByLog4j() {
		ClassPathResource resource = new ClassPathResource("/log4j.properties");
		if (resource.exists()) {
			String path;
			try {
				path = resource.getURI().toString();
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			System.err.println("pathpath:" + path);
			return path;
		}
		else {
			return null;
		}
	}
}
