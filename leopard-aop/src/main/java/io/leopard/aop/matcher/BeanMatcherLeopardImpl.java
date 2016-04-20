package io.leopard.aop.matcher;

public class BeanMatcherLeopardImpl implements BeanMatcher {

	@Override
	public Boolean matche(Object bean, String beanName, String className) {
		if (className.startsWith("io.leopard.site")) {
			return true;
		}
		if (className.startsWith("io.leopard")) {
			return false;
		}
		return null;
	}

}
