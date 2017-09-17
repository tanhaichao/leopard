package io.leopard.data.factory;

import java.util.Map;

import org.springframework.beans.factory.config.DependencyDescriptor;

public class BeanInjecterImpl implements BeanInjecter {

	@Override
	public Map<String, Object> findAutowireCandidates(String beanName, Class<?> requiredType, DependencyDescriptor descriptor) {
		return null;
	}

}
