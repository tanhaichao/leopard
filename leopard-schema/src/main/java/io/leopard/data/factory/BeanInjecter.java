package io.leopard.data.factory;

import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;

/**
 * Bean注入器
 * 
 * @author 谭海潮
 *
 */
public interface BeanInjecter {

	void findAutowireCandidates(BeanFactory beanFactory, String beanName, Class<?> requiredType, DependencyDescriptor descriptor, Map<String, Object> matchingBeans);
}
