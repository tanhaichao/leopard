package io.leopard.test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 按模块解析.
 * 
 * @author 阿海
 *
 */
public class ApplicationContextLocationModuleImpl implements ApplicationContextLocation {

	private String[] locations = { "/applicationContext.xml", "/applicationContext-service.xml", "/applicationContext-dao.xml" };

	private String defaultLocation = "/leopard/applicationContext.xml";

	@Override
	public String[] get() {
		for (String path : locations) {
			Resource resource = new ClassPathResource(path);
			if (resource.exists()) {
				if (new ClassPathResource(defaultLocation).exists()) {
					// TODO ahai 这里考虑使用扩展实现
					return new String[] { defaultLocation, path };
				}
				else {
					return new String[] { path };
				}
			}
		}
		return null;
	}
}
