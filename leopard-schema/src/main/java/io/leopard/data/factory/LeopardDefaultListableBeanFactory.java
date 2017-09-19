package io.leopard.data.factory;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class LeopardDefaultListableBeanFactory extends DefaultListableBeanFactory {

	private static BeanInjecter beanInjecter = new BeanInjecterImpl();

	public LeopardDefaultListableBeanFactory() {
		super();
	}

	public LeopardDefaultListableBeanFactory(BeanFactory parentBeanFactory) {
		super(parentBeanFactory);
	}

	@Override
	protected Map<String, Object> findAutowireCandidates(String beanName, Class<?> requiredType, DependencyDescriptor descriptor) {
		Map<String, Object> matchingBeans = super.findAutowireCandidates(beanName, requiredType, descriptor);
		if (matchingBeans.isEmpty()) {
			// logger.info("matchingBeans[" + beanName + "] [" + descriptor.getResolvableType() + "] is empty.");
			return this.findNoMatchingAutowireCandidates(beanName, requiredType, descriptor);
		}
		return matchingBeans;
	}

	protected Map<String, Object> findNoMatchingAutowireCandidates(String beanName, Class<?> requiredType, DependencyDescriptor descriptor) {
		// Class<?> clazz = descriptor.getResolvableType().resolve();
		// logger.info("findNoMatchingAutowireCandidates:" + clazz.getName());
		Map<String, Object> result = beanInjecter.findAutowireCandidates(this, beanName, requiredType, descriptor);
		if (result == null) {
			result = new LinkedHashMap<String, Object>();
		}
		return result;
	}

}
