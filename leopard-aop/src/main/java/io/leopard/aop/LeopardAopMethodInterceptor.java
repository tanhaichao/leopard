package io.leopard.aop;

import io.leopard.aop.matcher.MethodMatcher;
import io.leopard.aop.matcher.MethodMatcherImpl;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class LeopardAopMethodInterceptor implements MethodInterceptor {
	// 代理对象
	private Object target;

	public LeopardAopMethodInterceptor(Object target) {
		this.target = target;
	}

	private static MethodMatcher methodMatcher = new MethodMatcherImpl();

	@Override
	public Object intercept(Object bean, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		boolean matche = methodMatcher.matche(method, method.getName());
		if (matche) {
			System.err.println("LeopardAopMethodInterceptor:" + method.getDeclaringClass().getName() + ":" + method.toGenericString() + ":" + method.isBridge());
		}
		Object result = method.invoke(target, args);
		return result;
	}

}
