package io.leopard.data.env;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

/**
 * Leopard属性占位符配置器
 * 
 * @author 谭海潮
 *
 */
public class LeopardPropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	@Autowired
	private PlaceholderResolver placeholderResolver;

	@Autowired
	private PropertyFileResolverImpl propertyFileResolver;

	public LeopardPropertyPlaceholderConfigurer() {
		// System.err.println("LeopardPropertyPlaceholderConfigurer new.");
		super.setIgnoreResourceNotFound(true);
		super.setOrder(999);
		super.setIgnoreUnresolvablePlaceholders(true);
		super.setSystemPropertiesMode(SYSTEM_PROPERTIES_MODE_FALLBACK);

	}

	@PostConstruct
	public void init() {
		logger.info("init:" + this.getClass().getName());

		String env = EnvUtil.getEnv();
		Resource[] locations;
		try {
			locations = propertyFileResolver.getResources(env);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		super.setLocations(locations);
	}

	@Override
	// 执行顺序:2
	protected String resolvePlaceholder(String placeholder, Properties props) {
		String defaultValue = super.resolvePlaceholder(placeholder, props);
		return placeholderResolver.resolvePlaceholder(placeholder, props, defaultValue);
	}

	private Properties properties;

	@Override
	protected void convertProperties(Properties props) {
		super.convertProperties(props);
		this.properties = props;
	}

	/**
	 * 获取配置.
	 * 
	 * @param name
	 * @return
	 */
	public String getProperty(String name) {
		return properties.getProperty(name);
	}

}
