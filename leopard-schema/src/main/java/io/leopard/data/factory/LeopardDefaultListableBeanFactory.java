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

	// @Override
	// public Object doResolveDependency(DependencyDescriptor descriptor, String beanName, Set<String> autowiredBeanNames, TypeConverter typeConverter) throws BeansException {
	// Object obj = super.doResolveDependency(descriptor, beanName, autowiredBeanNames, typeConverter);
	// logger.info("doResolveDependency beanName:" + beanName + " autowiredBeanNames:" + autowiredBeanNames);
	// return obj;
	// }

	@Override
	protected Map<String, Object> findAutowireCandidates(String beanName, Class<?> requiredType, DependencyDescriptor descriptor) {
		Map<String, Object> matchingBeans = super.findAutowireCandidates(beanName, requiredType, descriptor);
		beanInjecter.findAutowireCandidates(this, beanName, requiredType, descriptor, matchingBeans);
		return matchingBeans;
	}

}
