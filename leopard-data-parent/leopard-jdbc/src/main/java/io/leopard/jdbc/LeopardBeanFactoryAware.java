package io.leopard.jdbc;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Primary;

public class LeopardBeanFactoryAware implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (LeopardBeanFactoryAware.beanFactory != null) {
			// RuntimeException e = new RuntimeException("beanFactory已初始化.");
			// e.printStackTrace();
			return;
		}

		// System.err.println("setBeanFactory:" + beanFactory);
		LeopardBeanFactoryAware.beanFactory = beanFactory;
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
	public static <T> T getSingleBean(BeanFactory beanFactory, Class<T> requiredType) throws BeansException {
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

	public static void updateBeanFactory(BeanFactory beanFactory) {
		LeopardBeanFactoryAware.beanFactory = beanFactory;
	}

	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public static void addBean(String beanName, Class<?> clazz) {
		if (!beanFactory.containsBean(beanName)) {
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
			registerBean(beanName, beanDefinitionBuilder.getRawBeanDefinition());
		}
	}

	// @PreDestroy
	// public void destroy() {
	// beanFactory = null;
	// }

	/**
	 * 
	 * @desc 向spring容器注册bean
	 * 
	 * @param beanName
	 * 
	 * @param beanDefinition
	 */

	private static void registerBean(String beanName, BeanDefinition beanDefinition) {
		BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) beanFactory;
		beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
	}

}
