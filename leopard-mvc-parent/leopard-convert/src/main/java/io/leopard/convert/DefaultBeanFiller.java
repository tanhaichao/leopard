package io.leopard.convert;

public class DefaultBeanFiller<S, T> implements BeanFiller {

	@Override
	public boolean supports(Class<?> sourceClass, Class<?> targetClass) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fill(Object source, Object target) {
		this.callback((S) source, (T) target);
	}

	public void callback(S source, T target) {

	}

}
