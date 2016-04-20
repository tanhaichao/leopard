package io.leopard.aop.matcher;

import java.util.ArrayList;
import java.util.List;

public class BeanMatcherImpl implements BeanMatcher {

	private List<BeanMatcher> beanMatcherList = new ArrayList<BeanMatcher>();

	public BeanMatcherImpl() {
		beanMatcherList.add(new BeanMatcherLeopardImpl());
		beanMatcherList.add(new BeanMatcherSpringImpl());
		beanMatcherList.add(new BeanMatcherComponentImpl());
	}

	@Override
	public Boolean matche(Object bean, String beanName, String className) {
		for (BeanMatcher beanMatcher : beanMatcherList) {
			Boolean matche = beanMatcher.matche(bean, beanName, className);
			if (matche != null) {
				return matche;
			}
		}
		return false;
	}

}