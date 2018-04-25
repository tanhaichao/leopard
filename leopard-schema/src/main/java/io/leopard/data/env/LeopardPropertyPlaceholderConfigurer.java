package io.leopard.data.env;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

public class LeopardPropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	private PlaceholderResolver placeholderResolver;

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
		this.placeholderResolver = getBean(beanFactory, PlaceholderResolver.class);
		this.propertyFileResolver = getBean(beanFactory, PropertyFileResolver.class);

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

	/**
	 * getBean
	 * 
	 * LeopardPropertyPlaceholderConfigurer初始化时，@Autowired注解还没有起作用
	 * 
	 * @param beanFactory
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public <T> T getBean(BeanFactory beanFactory, Class<T> requiredType) throws BeansException {
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
		Map<String, T> matchingBeans = factory.getBeansOfType(requiredType);
		if (matchingBeans.isEmpty()) {
			throw new NoSuchBeanDefinitionException(requiredType);
		}
		if (matchingBeans.size() == 1) {
			return matchingBeans.entrySet().iterator().next().getValue();
		}
		for (Entry<String, T> entry : matchingBeans.entrySet()) {
			T bean = entry.getValue();
			// TODO 还没有支持Bean有AOP
			Primary primary = bean.getClass().getDeclaredAnnotation(Primary.class);
			if (primary != null) {
				return bean;
			}
		}
		throw new NoUniqueBeanDefinitionException(requiredType, matchingBeans.keySet());
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
