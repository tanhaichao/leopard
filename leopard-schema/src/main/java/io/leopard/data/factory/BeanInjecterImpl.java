package io.leopard.data.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;

public class BeanInjecterImpl implements BeanInjecter {

	private List<BeanInjecter> list = new ArrayList<BeanInjecter>();

	public BeanInjecterImpl() {
		Iterator<BeanInjecter> iterator = ServiceLoader.load(BeanInjecter.class).iterator();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
	}

	@Override
	public void findAutowireCandidates(BeanFactory beanFactory, String beanName, Class<?> requiredType, DependencyDescriptor descriptor, Map<String, Object> matchingBeans) {
		for (BeanInjecter injecter : list) {
			injecter.findAutowireCandidates(beanFactory, beanName, requiredType, descriptor, matchingBeans);
		}
	}

}
