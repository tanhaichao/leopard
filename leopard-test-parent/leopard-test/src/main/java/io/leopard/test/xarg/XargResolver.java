package io.leopard.test.xarg;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.BeanFactory;

public interface XargResolver {

	void setBeanFactory(BeanFactory beanFactory);

	XargResolver match(MethodInvocation invocation, Class<?> clazz);

	Object invoke(MethodInvocation invocation) throws Exception;
}
