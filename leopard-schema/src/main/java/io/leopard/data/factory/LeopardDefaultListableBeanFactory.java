package io.leopard.data.factory;

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
		beanInjecter.findAutowireCandidates(this, beanName, requiredType, descriptor, matchingBeans);
		return matchingBeans;
	}

}
