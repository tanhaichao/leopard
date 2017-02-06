package io.leopard.jdbc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

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
