package io.leopard.aop.matcher;

public class BeanMatcherSpringImpl implements BeanMatcher {

	@Override
	public Boolean matche(Object bean, String beanName, String className) {
		if (className.startsWith("org.springframework")) {
			return false;
		}
		return null;
	}

}
