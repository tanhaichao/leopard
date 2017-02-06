package io.leopard.test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.BeanFactory;

import io.leopard.test.xarg.XargResolver;
import io.leopard.test.xarg.XargResolverImpl;

/**
 * 方法调用堆栈.
 * 
 * @author 阿海
 *
 */
public class TestAopInterceptor extends BeanNameAutoProxyCreator implements MethodInterceptor {

	private static final long serialVersionUID = 1L;

	private XargResolver xargResolver = new XargResolverImpl();

	public TestAopInterceptor() {

		String[] beanNames = new String[] { "*Controller", "*Service"//
				, "*Dao*"//
				, "*Queue"//
		};
		this.setBeanNames(beanNames);

		this.setProxyTargetClass(true);
		this.setInterceptorNames(new String[] { "testAopInterceptor" });
	}

	private static Set<String> IGNORED_BEAN_NAME_SET = new HashSet<String>();

	static {
		IGNORED_BEAN_NAME_SET.add("conversionService");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		super.setBeanFactory(beanFactory);
		xargResolver.setBeanFactory(beanFactory);
	}

	@Override
	protected boolean isMatch(String beanName, String mappedName) {

		if (IGNORED_BEAN_NAME_SET.contains(beanName)) {
			return false;
		}

		return super.isMatch(beanName, mappedName);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (true) {
			return invocation.proceed();
		}
		Class<?> clazz = invocation.getMethod().getDeclaringClass();
		// Object[] args = invocation.getArguments();
		// String params = StringUtils.join(args, ",");
		// logger.info("TestAopInterceptor invoke method:" + invocation.getMethod().toGenericString() + " params:" + params);
		XargResolver resolver = xargResolver.match(invocation, clazz);
		Object result;
		if (resolver == null) {
			result = invocation.proceed();
		}
		else {
			try {
				result = resolver.invoke(invocation);
			}
			catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}
		return result;
	}

}
