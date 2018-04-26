package io.leopard.data.env;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.io.Resource;

import io.leopard.jdbc.LeopardBeanFactoryAware;

/**
 * Leopard属性占位符配置器
 * 
 * @author 谭海潮
 *
 */
public class LeopardPropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	/**
	 * 占位符解析器
	 */
	private PlaceholderResolver placeholderResolver;

	/**
	 * 属性文件解析器
	 */
	private PropertyFileResolver propertyFileResolver;

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
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		super.setBeanFactory(beanFactory);
		this.placeholderResolver = LeopardBeanFactoryAware.getSingleBean(beanFactory, PlaceholderResolver.class);
		this.propertyFileResolver = LeopardBeanFactoryAware.getSingleBean(beanFactory, PropertyFileResolver.class);

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
		// System.err.println("resolvePlaceholderLei2 placeholder:" + placeholder);
		String defaultValue = super.resolvePlaceholder(placeholder, props);
		// if (value == null) {
		// System.err.println("resolvePlaceholderLei:" + resolvePlaceholderLei.getClass().getName());
		return placeholderResolver.resolvePlaceholder(placeholder, props, defaultValue);
		// }
		// return value;
	}

	// @Override
	// public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	// logger.info("postProcessBeanFactory");
	// super.postProcessBeanFactory(beanFactory);
	// }

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
